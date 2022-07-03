package com.dd.blog.controller;

import com.dd.blog.annotation.AccessLimit;
import com.dd.blog.annotation.GetPlaceInfo;
import com.dd.blog.annotation.IsLogin;
import com.dd.blog.dto.TempDTO;
import com.dd.blog.dto.UserBackDTO;
import com.dd.blog.service.RedisService;
import com.dd.blog.service.UserAuthService;
import com.dd.blog.utils.UserCounter;
import com.dd.blog.utils.UserThreadLocal;
import com.dd.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.dd.blog.constant.RedisPrefixConst.ONLINE_USERS;

/**
 * @author DD
 * @about 用户账号控制器
 * @date 2022/4/19 15:18
 */

@Api(tags = "用户账号模块")
@RestController
public class UserAuthController {

    @Autowired
    private UserCounter counter;

    @Autowired
    public UserAuthService userAuthService;

    @Autowired
    public RedisService redisService;

    @AccessLimit(seconds = 60, maxCount = 1)
    @ApiOperation(value = "发送邮箱验证码")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    @GetMapping("/users/code")
    public <T> Result sendCode(String username) {
        userAuthService.sendCode(username);
        return Result.ok();
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    @GetPlaceInfo
    public <T> Result register(@Valid @RequestBody UserVO user) {
        TempDTO tempDTO = userAuthService.register(user);
        if(tempDTO.getFlag()) {
            return Result.ok();
        } else {
            return Result.fail(tempDTO.getData());
        }
    }

    @Resource
    private HttpServletRequest request;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    @GetPlaceInfo
    public <T> Result login(UserVO userVO) {
//        System.out.println(userVO.getUsername() + " " + userVO.getPassword());
        return userAuthService.login(userVO);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "用户注销")
    @IsLogin
    public <T> Result logout() {
        //删除在线用户token
//        counter.remove(UserThreadLocal.get().getToken());
        return Result.ok();
    }

    @GetMapping("/admin/users/area")
    @ApiOperation(value = "获取用户区域分布")
    @IsLogin
    public <T>Result listUserAreas(ConditionVo condition) {
        return Result.ok(userAuthService.listUserAreas(condition));
    }

    @ApiOperation(value = "查询后台用户列表")
    @GetMapping("/admin/users")
    @IsLogin
    public Result<PageResult<UserBackDTO>> listUsers(ConditionVo condition) {
        return Result.ok(userAuthService.listUserBackDTO(condition));
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/users/password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO user) {
        if(userAuthService.updatePassword(user)) {
            return Result.ok();
        } else {
            return Result.fail("旧密码不正确");
        }
    }

    @ApiOperation(value = "修改管理员密码")
    @PutMapping("/admin/users/password")
    @IsLogin
    public Result<?> updateAdminPassword(@Valid @RequestBody PasswordVO passwordVO) {
        if(userAuthService.updateAdminPassword(passwordVO)) {
            return Result.ok();
        } else {
            return Result.fail("旧密码不正确");
        }

    }
}
