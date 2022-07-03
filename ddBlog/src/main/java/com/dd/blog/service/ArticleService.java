package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.*;
import com.dd.blog.entity.Article;
import com.dd.blog.vo.*;

import java.util.List;

/**
 * 文章服务
 * @author DD
 * @date 2022/4/6 15:35
 */


public interface ArticleService extends IService<Article> {

    /**
     * 查询首页文章
     */
    List<ArticleHomeDTO> listHomeArticle();

    /**
     * 根据文章id查询文章
     * @param articleId
     * @return
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * 列出归档信息
     * @return
     */
    PageResult<ArchiveDTO> listArchives();

    ArticlePreviewListDTO listArticleByCondition(ConditionVo condition);

    /**
     * 根据搜索条件进行搜索文章
     * @param condition
     * @return
     */
    List<ArticleSearchDTO> listArticleBySearch(ConditionVo condition);

    void saveArticleLike(Integer articleId);

    /**
     * 修改或更新文章
     * @param articleVo
     */
    void saveOrUpdateArticle(ArticleVo articleVo);

    PageResult<ArticleBackDTO> listArticleBacks(ConditionVo condition);

    ArticleUpdateDTO getArticleBackById(Integer articleId);

    void updateArticleTop(ArticleTopVO articleTopVO);

    void updateArticleDelete(DeleteVO deleteVO);

    void deleteArticles(List<Integer> articleIdList);
}
