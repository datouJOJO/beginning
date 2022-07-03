package com.dd.blog.controller;

import com.dd.blog.annotation.GetPlaceInfo;
import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dto.UserOnlineDTO;
import com.dd.blog.dto.UserRoleDTO;
import com.dd.blog.service.UserInfoService;
import com.dd.blog.utils.QiniuUtils;
import com.dd.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import static com.dd.blog.emuns.StatusCodeEnum.*;
import static com.dd.blog.constant.OptTypeConst.*;
/**
 * @author DD
 * @about 用户信息控制器
 * @date 2022/5/4 16:17
 */

@Api(tags = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    public UserInfoService userInfoService;

    @Autowired
    private QiniuUtils qiniuUtils;

    @PutMapping("/users/info")
    @ApiOperation(value = "更新用户信息")
    @IsLogin
    public <T> Result updateUserInfo(@RequestBody UserInfoVo userInfoVo) {
        userInfoService.updateUserInfo(userInfoVo);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/users/avatar")
    @IsLogin
    public <T> Result updateUserAvatar(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            String url = QiniuUtils.url + fileName;
            userInfoService.updateUserAvatar(url);
            return Result.ok(url);
        }
        return Result.fail(UPLOAD_FAIL.getMessage());
    }

    @ApiOperation(value = "绑定用户邮箱")
    @PostMapping("/users/email")
    @IsLogin
    public Result<?> saveUserEmail(@Valid @RequestBody EmailVO emailVO) {
        if(userInfoService.saveUserEmail(emailVO)) {
            return Result.ok();
        } else {
            return Result.fail("验证码错误！");
        }
    }

    @OptLog(optType = UPDATE)
    @IsLogin
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/admin/users/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.ok();
    }

    @OptLog(optType = UPDATE)
    @IsLogin
    @ApiOperation(value = "修改用户禁用状态")
    @PutMapping("/admin/users/disable")
    public Result<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.ok();
    }

    @ApiOperation(value = "查看在线用户")
    @GetMapping("/admin/users/online")
    public Result<PageResult<UserOnlineDTO>> listOnlineUsers(ConditionVo conditionVO) {
        return Result.ok(userInfoService.listOnlineUsers(conditionVO));
    }
}
