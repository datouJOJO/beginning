package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.TempDTO;
import com.dd.blog.dto.UserAreaDTO;
import com.dd.blog.dto.UserBackDTO;
import com.dd.blog.entity.UserAuth;
import com.dd.blog.vo.*;

import java.util.List;

/**
 * @about 用户账号服务
 * @author DD
 * @date 2022/4/19 15:16
 */


public interface UserAuthService extends IService<UserAuth> {
    /**
     * 向指定的用户邮箱发送验证码
     * @param username
     */
    void sendCode(String username);

    TempDTO register(UserVO user);

    <T>Result login(UserVO userVO);

    PageResult<UserBackDTO> listUserBackDTO(ConditionVo condition);

    List<UserAreaDTO> listUserAreas(ConditionVo condition);

    boolean updatePassword(UserVO user);

    boolean updateAdminPassword(PasswordVO passwordVO);
}
