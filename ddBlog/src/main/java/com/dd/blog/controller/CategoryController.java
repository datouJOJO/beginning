package com.dd.blog.controller;

import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dto.CategoryBackDTO;
import com.dd.blog.service.CategoryService;
import com.dd.blog.vo.CategoryVO;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import static com.dd.blog.constant.OptTypeConst.*;
/**
 * @author DD
 * @about
 * @date 2022/4/30 20:46
 */

@RestController
@Api(tags = "分类模块")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "获取多个分类")
    public <T> Result listCategories() {
        return Result.ok(categoryService.listCategories());
    }

    @ApiOperation(value = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public <T>Result listCategoryBySearch(ConditionVo condition) {
        return Result.ok(categoryService.listCategoryBySearch(condition));
    }

    @ApiOperation(value = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryBackDTO>> listBackCategories(ConditionVo condition) {
        return Result.ok(categoryService.listBackCategories(condition));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @IsLogin
    @ApiOperation(value = "添加或修改分类")
    @PostMapping("/admin/categories")
    public Result<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        boolean flag = categoryService.saveOrUpdateCategory(categoryVO);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail("分类名已存在");
        }
    }

    @OptLog(optType = REMOVE)
    @IsLogin
    @DeleteMapping("/admin/categories")
    @ApiOperation(value = "删除分类")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIdList) {
        boolean flag = categoryService.deleteCategory(categoryIdList);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail("删除失败，该分类下存在文章");
        }
    }
}
