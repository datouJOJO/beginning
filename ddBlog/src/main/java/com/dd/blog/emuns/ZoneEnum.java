package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DD
 * @date 2022/4/5 1:24
 */

@Getter
@AllArgsConstructor
public enum ZoneEnum {

    SHANGHAI("Asia/Shanghai", "中国上海");
    /**
     * 时区
     */
    private final String zone;
    /**
     * 描述
     */
    private final String desc;
}
