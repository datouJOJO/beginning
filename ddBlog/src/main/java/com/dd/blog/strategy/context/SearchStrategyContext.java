package com.dd.blog.strategy.context;

import com.dd.blog.dto.ArticleSearchDTO;
import com.dd.blog.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.dd.blog.emuns.SearchModeEnum.*;

/**
 * @author DD
 * @about
 * @date 2022/5/2 12:12
 */

@Service
public class SearchStrategyContext {

    /**
     * 获取配置文件中的搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;

    @Autowired
    private Map<String, SearchStrategy> stringSearchStrategyMap;

    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        return stringSearchStrategyMap.get(getStrategy(searchMode)).searchArticle(keywords);
    }
}
