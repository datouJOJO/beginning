package com.dd.blog.controller;

import com.dd.blog.annotation.IsLogin;
import com.dd.blog.dto.LabelOptionDTO;
import com.dd.blog.dto.MenuDTO;
import com.dd.blog.dto.UserMenuDTO;
import com.dd.blog.service.MenuService;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.MenuVO;
import com.dd.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author DD
 * @about 菜单控制器
 * @date 2022/5/6 12:03
 */

@RestController
@Api(tags = "菜单模块")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/admin/user/menus")
    @ApiOperation(value = "根据用户能力显示菜单列表")
    @IsLogin
    public <T> Result listUserMenu() {
        return Result.ok(menuService.listUserMenus());
    }

    @ApiOperation(value = "查看菜单列表")
    @GetMapping("/admin/menus")
    public Result<List<MenuDTO>> listMenus(ConditionVo conditionVO) {
        return Result.ok(menuService.listMenus(conditionVO));
    }

    @ApiOperation(value = "查看角色菜单选项")
    @GetMapping("/admin/role/menus")
    public Result<List<LabelOptionDTO>> listMenuOptions() {
        return Result.ok(menuService.listMenuOptions());
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/admin/menus/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId){
        if(menuService.deleteMenu(menuId)) {
            return Result.ok();
        } else {
            return Result.fail("菜单下有角色关联 删除失败");
        }
    }

    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/admin/menus")
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.ok();
    }
}
