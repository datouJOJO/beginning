package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.RoleDao;
import com.dd.blog.dto.RoleDTO;
import com.dd.blog.dto.UserRoleDTO;
import com.dd.blog.entity.Role;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.RoleVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DD
 * @about 用户角色
 * @date 2022/5/9 18:52
 */

public interface RoleService extends IService<Role> {
    List<UserRoleDTO> listUserRoles();

    PageResult<RoleDTO> listRoles(ConditionVo conditionVO);

    boolean saveOrUpdateRole(RoleVO roleVO);

    boolean deleteRoles(List<Integer> roleIdList);
}
