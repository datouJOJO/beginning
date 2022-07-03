package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.dto.RoleDTO;
import com.dd.blog.entity.Role;
import com.dd.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/5/9 18:54
 */

@Repository
public interface RoleDao extends BaseMapper<Role> {
    List<RoleDTO> listRoles(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVo conditionVO);
}
