package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.ArticleDao;
import com.dd.blog.dao.CategoryDao;
import com.dd.blog.dto.CategoryBackDTO;
import com.dd.blog.dto.CategoryDTO;
import com.dd.blog.dto.CategoryOptionDTO;
import com.dd.blog.entity.Article;
import com.dd.blog.entity.Category;
import com.dd.blog.service.CategoryService;
import com.dd.blog.utils.BeanCopyUtils;
import com.dd.blog.utils.PageUtils;
import com.dd.blog.vo.CategoryVO;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author DD
 * @about 分类服务
 * @date 2022/4/30 20:49
 */

@Service
public class CategoryServiceImpl  extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    public CategoryDao categoryDao;

    @Override
    public PageResult<CategoryDTO> listCategories() {
        return new PageResult<>(categoryDao.listCategoryDTO(), categoryDao.selectCount(null));
    }

    @Override
    public List<CategoryOptionDTO> listCategoryBySearch(ConditionVo condition) {
        // 搜索分类
        List<Category> categoryList = categoryDao.selectList(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords())
                .orderByDesc(Category::getId));
        return BeanCopyUtils.copyList(categoryList, CategoryOptionDTO.class);
    }

    @Override
    public PageResult<CategoryBackDTO> listBackCategories(ConditionVo condition) {
        // 查询分类数量
        Integer count = categoryDao.selectCount(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询分类列表
        List<CategoryBackDTO> categoryList = categoryDao.listCategoryBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(categoryList, count);
    }

    @Override
    public boolean saveOrUpdateCategory(CategoryVO categoryVO) {
        // 判断分类名重复
        Category existCategory = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            return false;
        }
        Category category = Category.builder()
                .id(categoryVO.getId())
                .categoryName(categoryVO.getCategoryName())
                .build();
        this.saveOrUpdate(category);
        return true;
    }

    @Autowired
    private ArticleDao articleDao;

    @Override
    public boolean deleteCategory(List<Integer> categoryIdList) {
        // 查询分类id下是否有文章
        Integer count = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, categoryIdList));
        if (count > 0) {
            return false;
        }
        categoryDao.deleteBatchIds(categoryIdList);
        return true;
    }
}
