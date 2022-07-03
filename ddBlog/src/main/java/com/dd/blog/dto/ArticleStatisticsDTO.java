package com.dd.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DD
 * @about 文章统计
 * @date 2022/5/7 13:47
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleStatisticsDTO {
    /**
     * 日期
     */
    private String date;

    /**
     * 数量
     */
    private Integer count;
}
