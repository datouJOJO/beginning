package com.dd.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.UserAuthDao;
import com.dd.blog.dao.UserInfoDao;
import com.dd.blog.dao.UserRoleDao;
import com.dd.blog.dto.*;
import com.dd.blog.entity.UserAuth;
import com.dd.blog.entity.UserInfo;
import com.dd.blog.entity.UserRole;
import com.dd.blog.exception.BizException;
import com.dd.blog.service.BlogInfoService;
import com.dd.blog.service.RedisService;
import com.dd.blog.service.UserAuthService;
import com.dd.blog.utils.JWTUtils;
import com.dd.blog.utils.PageUtils;
import com.dd.blog.utils.UserCounter;
import com.dd.blog.utils.UserThreadLocal;
import com.dd.blog.vo.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.dd.blog.emuns.RoleEnum.USER;
import static com.dd.blog.emuns.ZoneEnum.SHANGHAI;
import static com.dd.blog.utils.CommonUtils.checkEmail;
import static com.dd.blog.utils.CommonUtils.getRandomCode;
import static com.dd.blog.constant.MQPrefixConst.*;
import static com.dd.blog.constant.RedisPrefixConst.*;
import static com.dd.blog.emuns.LoginEnum.*;
import static com.dd.blog.constant.CommonConst.*;
import static com.dd.blog.emuns.UserAreaEnum.*;
/**
 * @author DD
 * @about 用户账号服务
 * @date 2022/4/19 15:37
 */

@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuth> implements UserAuthService {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Autowired
    public RedisService redisService;

    @Autowired
    public UserAuthDao userAuthDao;

    @Autowired
    public UserInfoDao userInfoDao;

    @Autowired
    public UserRoleDao userRoleDao;

    @Autowired
    public BlogInfoService blogInfoService;

    @Autowired
    private UserCounter counter;

    @Override
    public void sendCode(String username) {
        // 校验账号是否合法
        if (!checkEmail(username)) {
            throw new BizException("请输入正确邮箱");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("验证码")
                .content("您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！")
                .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TempDTO register(UserVO user) {
        TempDTO tempDTO = new TempDTO();
        if(!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
            tempDTO.setFlag(false);
            tempDTO.setData("验证码错误");
            return tempDTO;
            //            throw new BizException("验证码错误");
        }
        if(checkUser(user)) {
            tempDTO.setFlag(false);
            tempDTO.setData("邮箱已被注册");
            return tempDTO;
//            throw new BizException("邮箱已被注册");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(user.getUsername())
                .nickname(DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(blogInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoDao.insert(userInfo);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(USER.getRoleId())
                .build();
        userRoleDao.insert(userRole);
        // 新增用户账号
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .loginType(EMAIL.getType())
                .ipAddress(UserThreadLocal.get().getIp())
                .ipSource(UserThreadLocal.get().getPlace())
                .build();
        userAuthDao.insert(userAuth);
        tempDTO.setFlag(true);
        return tempDTO;
    }

    public <T>Result login(UserVO userVO) {
        //校验账号
        if(!checkUser(userVO)) {
            return Result.fail("该账号尚未注册");
        }
        //校验密码
        if(!checkPwd(userVO)) {
            return Result.fail("账号或密码错误");
        }
        //获取需要的信息
        UserAuth userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getId, UserAuth::getUserInfoId, UserAuth::getUsername, UserAuth::getLoginType)
                .eq(UserAuth::getUsername, userVO.getUsername()));
        UserInfo userInfo = userInfoDao.selectOne(new LambdaQueryWrapper<UserInfo>()
                .select(UserInfo::getEmail, UserInfo::getNickname, UserInfo::getAvatar, UserInfo::getIntro, UserInfo::getWebSite)
                .eq(UserInfo::getId, userAuth.getUserInfoId()));
        //生成token
        String token = JWTUtils.getToken(userAuth.getUserInfoId());
//        System.out.println(JWTUtils.getId(token));
        //更新一下最近登陆时间
        updateLastLoginTime(userAuth);
        //id加入当前在线用户 加入redis
//        counter.insertId(token);
        redisService.setAdd(ONLINE_USERS, token);
        //封装对象
        LoginDTO loginDTO = LoginDTO.builder()
                .id(userAuth.getId())
                .userInfoId(userAuth.getUserInfoId())
                .email(userInfo.getEmail())
                .loginType(userAuth.getLoginType())
                .username(userAuth.getUsername())
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .webSite(userInfo.getWebSite())
                .ipAddress(UserThreadLocal.get().getIp())
                .ipSource(UserThreadLocal.get().getPlace())
                .token(token)
                .build();
        return Result.ok(loginDTO);
    }

    @Override
    public PageResult<UserBackDTO> listUserBackDTO(ConditionVo condition) {
        // 获取后台用户数量
        Integer count = userAuthDao.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 获取后台用户列表
        List<UserBackDTO> userBackDTOList = userAuthDao.listUsers(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(userBackDTOList, count);
    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVo condition) {
        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();
        switch(Objects.requireNonNull(getUserAreaType(condition.getType()))) {
            case USER:
                Object userArea = redisService.get(USER_AREA);
                if(Objects.nonNull(userArea)) {
                    userAreaDTOList = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOList;
            case VISITOR:
                // 查询游客区域分布
                Map<String, Object> visitorArea = redisService.hashGetAll(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOList = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                                .collect(Collectors.toList());
                }
                return userAreaDTOList;
            default:
                break;
        }

        return userAreaDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePassword(UserVO user) {
        if(!checkPwd(user)) {
            return false;
        }
        // 根据用户名修改密码
        userAuthDao.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getPassword, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .eq(UserAuth::getUsername, user.getUsername()));
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAdminPassword(PasswordVO passwordVO) {
        // 查询旧密码是否正确
        UserAuth user = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getId, UserThreadLocal.get().getUserId()));
        // 正确则修改密码，错误则提示不正确
        if (Objects.nonNull(user) && BCrypt.checkpw(passwordVO.getOldPassword(), user.getPassword())) {
            UserAuth userAuth = UserAuth.builder()
                    .id(UserThreadLocal.get().getUserId())
                    .password(BCrypt.hashpw(passwordVO.getNewPassword(), BCrypt.gensalt()))
                    .build();
            userAuthDao.updateById(userAuth);
            return true;
        } else {
            return false;
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateLastLoginTime(UserAuth userAuth) {
        UserAuth user = UserAuth.builder()
                .id(userAuth.getId())
                .ipAddress(UserThreadLocal.get().getIp())
                .ipSource(UserThreadLocal.get().getPlace())
                .lastLoginTime(LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())))
                .build();
        userAuthDao.updateById(user);
    }

    /**
     * 校验角色信息是否合法
     * 验证码是否正确对应
     * @param user
     * @return
     */
    public boolean checkUser(UserVO user) {
        //查询用户名是否存在
        UserAuth userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }

    /**
     * 校验密码是否正确
     * @param user
     * @return
     */
    public boolean checkPwd(UserVO user) {
        UserAuth userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, user.getUsername()));
        return BCrypt.checkpw(user.getPassword(), userAuth.getPassword());
    }

    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("62591102", BCrypt.gensalt()));
        System.out.println(BCrypt.checkpw("123456", "$2a$10$AkxkZaqcxEXdiNE1nrgW1.ms3aS9C5ImXMf8swkWUJuFGMqDl.TPW"));
        System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()));
    }

    /**
     * 定时更新用户区域统计结果到数据库中
     * 然后再更新redis
     */
    @Scheduled(cron = "0 0 * * * ?" , zone = "Asia/Shanghai")
    public void statisticalUserArea() {
        //统计用户地域分布
        Map<String, Long> userAreaMap = userAuthDao.selectList(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getIpSource))
                .stream()
                .map(item -> {
                    if(StringUtils.isNotBlank(item.getIpSource())) {
                        return item.getIpSource().substring(0, 2)
                                .replaceAll(PROVINCE, "")
                                .replaceAll(CITY, "");
                    }
                    return UNKNOWN;
                })
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        List<UserAreaDTO> userAreaDTOList = userAreaMap.entrySet().stream()
                .map(item -> UserAreaDTO.builder()
                        .name(item.getKey())
                        .value(item.getValue())
                        .build())
                .collect(Collectors.toList());
        redisService.set(USER_AREA, JSON.toJSONString(userAreaDTOList));
    }

}
