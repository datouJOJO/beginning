package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.dto.*;
import com.dd.blog.entity.Article;
import com.dd.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * 文章
 * @author DD
 * @date 2022/4/5 1:04
 */

@Repository
public interface ArticleDao extends BaseMapper<Article> {

    /**
     * 查询首页文章
     * @param current
     * @param size
     * @return
     */
    List<ArticleHomeDTO> listHomeArticle(@Param("current")Long current, @Param("size") Long size);

    /**
     * 查询推荐文章 与当前文章id不相同
     * @param articleId
     * @return
     */
    List<ArticleRecommendDTO> listRecommendArticles(@Param("articleId") Integer articleId);

    /**
     * 根据文章id查询文章
     * @param articleId
     * @return
     */
    ArticleDTO getArticleById(@Param("articleId")Integer articleId);

    List<ArticlePreviewDTO> listArticleByCondition(@Param("current") Long current,@Param("size") Long size,@Param("condition") ConditionVo condition);

    List<ArticleStatisticsDTO> listArticleStatistics();

    Integer countArticleBacks(@Param("condition") ConditionVo condition);

    List<ArticleBackDTO> listArticleBacks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);
}
