package com.dd.blog.strategy;

import com.dd.blog.dto.ArticleSearchDTO;

import java.util.List;

/**
 * @author DD
 * @about 搜索策略
 * @date 2022/5/2 11:39
 */

public interface SearchStrategy {

    /**
     * 按照对应的关键字搜索标题和文章内容
     * 将搜索到的文章封装成list
     * @param keywords
     * @return
     */
    List<ArticleSearchDTO> searchArticle(String keywords);
}
