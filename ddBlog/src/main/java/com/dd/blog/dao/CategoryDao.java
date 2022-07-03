package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.controller.CategoryController;
import com.dd.blog.dto.CategoryBackDTO;
import com.dd.blog.dto.CategoryDTO;
import com.dd.blog.entity.Category;
import com.dd.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类
 * @author DD
 * @date 2022/4/5 1:04
 */

@Repository
public interface CategoryDao extends BaseMapper<Category> {

    List<CategoryDTO> listCategoryDTO();

    List<CategoryBackDTO> listCategoryBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);
}
