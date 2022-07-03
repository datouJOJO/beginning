package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * 用户信息
 * @author DD
 * @date 2022/4/5 1:03
 */

@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {
}
