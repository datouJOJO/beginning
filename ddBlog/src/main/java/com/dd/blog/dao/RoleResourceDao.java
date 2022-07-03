package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.RoleResource;
import org.springframework.stereotype.Repository;

/**
 * @author DD
 * @about 角色对应资源数据持久层
 * @date 2022/5/18 16:13
 */

@Repository
public interface RoleResourceDao extends BaseMapper<RoleResource> {
}
