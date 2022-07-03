package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.CategoryBackDTO;
import com.dd.blog.dto.CategoryDTO;
import com.dd.blog.dto.CategoryOptionDTO;
import com.dd.blog.entity.Category;
import com.dd.blog.vo.CategoryVO;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;

import java.util.List;

/**
*@about 分类服务
*@author DD
*@date 2022/4/30 20:49
*/


public interface CategoryService extends IService<Category> {

    /**
     * 列出多个种类
     * 和种类个数
     * @return
     */
    PageResult<CategoryDTO> listCategories();

    /**
     * 根据条件搜索文章分类
     * @param condition
     * @return
     */
    List<CategoryOptionDTO> listCategoryBySearch(ConditionVo condition);

    PageResult<CategoryBackDTO> listBackCategories(ConditionVo condition);

    boolean saveOrUpdateCategory(CategoryVO categoryVO);

    boolean deleteCategory(List<Integer> categoryIdList);
}
