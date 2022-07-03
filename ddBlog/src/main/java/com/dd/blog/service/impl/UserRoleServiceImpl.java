package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.UserRoleDao;
import com.dd.blog.entity.UserRole;
import com.dd.blog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author DD
 * @about
 * @date 2022/5/9 14:47
 */

@Service
public class UserRoleServiceImpl  extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
}
