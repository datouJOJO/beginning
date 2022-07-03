package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举
 * @author DD
 * @date 2022/4/5 10:58
 */

@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {

    PUBLIC(1, "公开");

    private final Integer status;
    private final String desc;
}
