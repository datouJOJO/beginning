package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DD
 * @about 登陆类型枚举
 * @date 2022/4/25 21:29
 */

@Getter
@AllArgsConstructor
public enum LoginEnum {
    EMAIL(1, "邮箱", "");

    /**
     * 种类
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 策略
     */
    private final String strategy;
}
