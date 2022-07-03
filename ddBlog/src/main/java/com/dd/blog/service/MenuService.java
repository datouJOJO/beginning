package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.LabelOptionDTO;
import com.dd.blog.dto.MenuDTO;
import com.dd.blog.dto.UserMenuDTO;
import com.dd.blog.entity.Menu;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.MenuVO;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/5/6 12:36
 */

public interface MenuService extends IService<Menu>{

    List<UserMenuDTO> listUserMenus();

    List<MenuDTO> listMenus(ConditionVo conditionVO);

    List<LabelOptionDTO> listMenuOptions();

    boolean deleteMenu(Integer menuId);

    void saveOrUpdateMenu(MenuVO menuVO);
}
