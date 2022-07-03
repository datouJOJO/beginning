package com.dd.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dd.blog.dao.UserAuthDao;
import com.dd.blog.dao.UserInfoDao;
import com.dd.blog.dto.ConnectionInfo;
import com.dd.blog.dto.UserOnlineDTO;
import com.dd.blog.entity.UserAuth;
import com.dd.blog.entity.UserInfo;
import com.dd.blog.entity.UserRole;
import com.dd.blog.service.RedisService;
import com.dd.blog.service.UserInfoService;
import com.dd.blog.service.UserRoleService;
import com.dd.blog.utils.JWTUtils;
import com.dd.blog.utils.PageUtils;
import com.dd.blog.utils.UserCounter;
import com.dd.blog.utils.UserThreadLocal;
import com.dd.blog.vo.*;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dd.blog.constant.RedisPrefixConst.*;

/**
 * @author DD
 * @about
 * @date 2022/5/4 16:28
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRoleService userRoleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVo userInfoVo) {
//        System.out.println("----------------------------" + UserThreadLocal.get().getUserId());
//        System.out.println(userInfoVo.getNickname());
//        System.out.println(userInfoVo.getIntro());
//        System.out.println(userInfoVo.getWebSite());
        UserInfo userInfo = UserInfo.builder()
                .id(UserThreadLocal.get().getUserId())
                .nickname(userInfoVo.getNickname())
                .intro(userInfoVo.getIntro())
                .webSite(userInfoVo.getWebSite())
                .build();
        userInfoDao.updateById(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserAvatar(String url) {
        UserInfo userInfo = UserInfo.builder()
                .id(UserThreadLocal.get().getUserId())
                .avatar(url)
                .build();
        userInfoDao.updateById(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveUserEmail(EmailVO emailVO) {
        if (!emailVO.getCode().equals(redisService.get(USER_CODE_KEY + emailVO.getEmail()).toString())) {
            return false;
        }
        UserInfo userInfo = UserInfo.builder()
                .id(UserThreadLocal.get().getUserId())
                .email(emailVO.getEmail())
                .build();
        userInfoDao.updateById(userInfo);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        // 更新用户角色和昵称
        UserInfo userInfo = UserInfo.builder()
                .id(userRoleVO.getUserInfoId())
                .nickname(userRoleVO.getNickname())
                .build();
        userInfoDao.updateById(userInfo);
        // 删除用户角色重新添加
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVO.getUserInfoId()));
        List<UserRole> userRoleList = userRoleVO.getRoleIdList().stream()
                .map(roleId -> UserRole.builder()
                        .roleId(roleId)
                        .userId(userRoleVO.getUserInfoId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserDisable(UserDisableVO userDisableVO) {
        // 更新用户禁用状态
        UserInfo userInfo = UserInfo.builder()
                .id(userDisableVO.getId())
                .isDisable(userDisableVO.getIsDisable())
                .build();
        userInfoDao.updateById(userInfo);
    }

    @Autowired
    private UserCounter counter;

    @Autowired
    private UserAuthDao userAuthDao;

    @Override
    public PageResult<UserOnlineDTO> listOnlineUsers(ConditionVo conditionVO) {
        //获取在线用户id
//        Set<String> ids = counter.getOnlineUsers();
        Set<Object> t = redisService.getSet(ONLINE_USERS);
        if(Objects.isNull(t)) return new PageResult<>();
         List<UserOnlineDTO> userOnlineDTOS = t.stream().map(item -> {
             if(Objects.nonNull(JWTUtils.getId(item.toString()))) {
                 UserInfo userInfo = userInfoDao.selectOne(new LambdaQueryWrapper<UserInfo>()
                         .select(UserInfo::getId, UserInfo::getNickname, UserInfo::getAvatar)
                         .like(StringUtils.isNotBlank(conditionVO.getKeywords()), UserInfo::getNickname, conditionVO.getKeywords())
                         .eq(UserInfo::getId, JWTUtils.getId(item.toString())));
                 if(userInfo == null) {
                     return null;
                 }
                 UserAuth userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                         .select(UserAuth::getIpSource, UserAuth::getIpAddress, UserAuth::getLastLoginTime)
                         .eq(UserAuth::getUserInfoId, userInfo.getId()));
                 return UserOnlineDTO.builder()
                         .userInfoId(userInfo.getId())
                         .nickname(userInfo.getNickname())
                         .avatar(userInfo.getAvatar())
                         .ipAddress(userAuth.getIpAddress())
                         .ipSource(userAuth.getIpSource())
                         .lastLoginTime(userAuth.getLastLoginTime())
                         .build();
                } else {
                    redisService.setRemove(ONLINE_USERS, item.toString());
                    return null;
                }
             }
        ).collect(Collectors.toList());
        //剔除空数据
        List<UserOnlineDTO> tmp = new ArrayList<>();
        for(UserOnlineDTO user : userOnlineDTOS) {
            if(user != null) {
                tmp.add(user);
            }
        }
        // 执行分页
        int fromIndex = PageUtils.getLimitCurrent().intValue();
        int size = PageUtils.getSize().intValue();
        int toIndex = tmp.size() - fromIndex > size ? fromIndex + size : tmp.size();
        List<UserOnlineDTO> userOnlineList = tmp.subList(fromIndex, toIndex);
        return new PageResult<>(userOnlineList, tmp.size());
    }
}
