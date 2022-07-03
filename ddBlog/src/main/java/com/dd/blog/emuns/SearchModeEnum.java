package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DD
 * @about
 * @date 2022/5/2 14:16
 */

@Getter
@AllArgsConstructor
public enum SearchModeEnum {

    MYSQL("mysql", "MySqlSearchStrategyImpl"),
    ELASTICSEARCH("elasticsearch", "ElasticSearchImpl");

    /**
     * 模式
     */
    private final String mode;
    /**
     * 策略
     */
    private final String strategy;

    public static String getStrategy(String mode) {
        for(SearchModeEnum value : SearchModeEnum.values()) {
            if(value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }
}
