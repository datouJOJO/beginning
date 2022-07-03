package com.dd.blog.service;

import com.dd.blog.dto.UserOnlineDTO;
import com.dd.blog.vo.*;

/**
 * @author DD
 * @about
 * @date 2022/5/4 16:28
 */
public interface UserInfoService {
    //更新用户信息
    void updateUserInfo(UserInfoVo userInfoVo);
    //更新用户头像
    void updateUserAvatar(String url);

    boolean saveUserEmail(EmailVO emailVO);

    void updateUserRole(UserRoleVO userRoleVO);

    void updateUserDisable(UserDisableVO userDisableVO);

    PageResult<UserOnlineDTO> listOnlineUsers(ConditionVo conditionVO);
}
