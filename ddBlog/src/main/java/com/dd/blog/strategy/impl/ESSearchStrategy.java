package com.dd.blog.strategy.impl;

import com.dd.blog.dto.ArticleSearchDTO;
import com.dd.blog.strategy.SearchStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DD
 * @about es搜索策略
 * @date 2022/5/2 11:57
 */

@Service("ElasticSearchImpl")
public class ESSearchStrategy implements SearchStrategy {



    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        return null;
    }
}
