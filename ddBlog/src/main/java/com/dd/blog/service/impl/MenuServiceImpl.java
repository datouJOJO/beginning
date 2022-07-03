package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.MenuDao;
import com.dd.blog.dao.RoleMenuDao;
import com.dd.blog.dto.LabelOptionDTO;
import com.dd.blog.dto.MenuDTO;
import com.dd.blog.dto.UserMenuDTO;
import com.dd.blog.entity.Menu;
import com.dd.blog.entity.RoleMenu;
import com.dd.blog.service.MenuService;
import com.dd.blog.utils.BeanCopyUtils;
import com.dd.blog.utils.UserThreadLocal;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.dd.blog.constant.CommonConst.*;
/**
 * @author DD
 * @about
 * @date 2022/5/6 12:37
 */

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService{

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<UserMenuDTO> listUserMenus() {
        //获取所有菜单
        List<Menu> menuList = menuDao.listMenusByUserInfoId(UserThreadLocal.get().getUserId());
        //查找出所有一级菜单
        List<Menu> catalogList = listCatalog(menuList);
        //获取一级目录下的二级菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        return convertUserMenuList(catalogList, childrenMap);
    }

    @Override
    public List<MenuDTO> listMenus(ConditionVo conditionVO) {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), Menu::getName, conditionVO.getKeywords()));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        List<MenuDTO> menuDTOList = catalogList.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtils.copyObject(item, MenuDTO.class);
            // 获取目录下的菜单排序
            List<MenuDTO> list = BeanCopyUtils.copyList(childrenMap.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTO.setChildren(list);
            childrenMap.remove(item.getId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum)).collect(Collectors.toList());
        // 若还有菜单未取出则拼接
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Menu> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOList.addAll(childrenDTOList);
        }
        return menuDTOList;
    }

    @Override
    public List<LabelOptionDTO> listMenuOptions() {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            // 获取目录下的菜单排序
            List<LabelOptionDTO> list = new ArrayList<>();
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> LabelOptionDTO.builder()
                                .id(menu.getId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(list)
                    .build();
        }).collect(Collectors.toList());
    }

    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public boolean deleteMenu(Integer menuId) {
        // 查询是否有角色关联
        Integer count = roleMenuDao.selectCount(new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getMenuId, menuId));
        if (count > 0) {
            return false;
        }
        // 查询子菜单
        List<Integer> menuIdList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId)
                .eq(Menu::getParentId, menuId))
                .stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        menuIdList.add(menuId);
        menuDao.deleteBatchIds(menuIdList);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(MenuVO menuVO) {
        Menu menu = BeanCopyUtils.copyObject(menuVO, Menu.class);
        this.saveOrUpdate(menu);
    }

    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenMap) {
        return catalogList.stream().map(item -> {
           UserMenuDTO userMenuDTO = new UserMenuDTO();
           List<UserMenuDTO> list = new ArrayList<>();
           //所有子菜单们
           List<Menu> children = childrenMap.get(item.getId());
           if(CollectionUtils.isNotEmpty(children)) {
               userMenuDTO = BeanCopyUtils.copyObject(item, UserMenuDTO.class);
               list = children.stream()
                       .sorted(Comparator.comparing(Menu::getOrderNum))
                       .map(menu -> {
                           UserMenuDTO dto = BeanCopyUtils.copyObject(menu, UserMenuDTO.class);
                           dto.setHidden(menu.getIsHidden().equals(TRUE));
                           return dto;
                       }).collect(Collectors.toList());
           } else {
                // 一级菜单处理
               userMenuDTO.setPath(item.getPath());
               userMenuDTO.setComponent(COMPONENT);
               list.add(UserMenuDTO.builder()
                       .path("")
                       .name(item.getName())
                       .icon(item.getIcon())
                       .component(item.getComponent())
                       .build());
           }
            userMenuDTO.setHidden(item.getIsHidden().equals(TRUE));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }

    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
    }

    private List<Menu> listCatalog(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
    }
}
