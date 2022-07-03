package com.dd.blog.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dd.blog.dao.ArticleDao;
import com.dd.blog.dto.ArticleSearchDTO;
import com.dd.blog.entity.Article;
import com.dd.blog.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dd.blog.constant.CommonConst.*;
import static com.dd.blog.emuns.ArticleStatusEnum.*;
/**
 * @author DD
 * @about mysql搜索策略
 * @date 2022/5/2 11:56
 */

@Service("MySqlSearchStrategyImpl")
public class MySqlSearchStrategy implements SearchStrategy {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        if(StringUtils.isBlank(keywords)) {
            return new ArrayList<>();
        }
        //搜索标题或正文与搜索条件匹配的文章
        List<Article> articleList = articleDao.selectList(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .and(i -> i.like(Article::getArticleTitle, keywords)
                        .or()
                        .like(Article::getArticleContent, keywords)));
        //封装对象
        return articleList.stream().map(item -> {
            String articleContent = item.getArticleContent();
            int index = item.getArticleContent().indexOf(keywords);
            //正文高亮
            if(index != -1) {
                int preIndex = index > 25 ? index - 25 : 0;
                String preText = item.getArticleContent().substring(preIndex, index);
                int last = index + keywords.length();
                int postLength = item.getArticleContent().length() - last;
                int postIndex = postLength > 175 ? last + 175 : last + postLength;
                String postText = item.getArticleContent().substring(index, postIndex);
                //高亮显示
                articleContent = (preText + postText).replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            }
            //标题高亮
            String articleTitile = item.getArticleTitle().replaceAll(keywords, PRE_TAG + keywords + POST_TAG);
            return ArticleSearchDTO.builder()
                    .id(item.getId())
                    .articleTitle(articleTitile)
                    .articleContent(articleContent)
                    .build();
        }).collect(Collectors.toList());
    }
}
