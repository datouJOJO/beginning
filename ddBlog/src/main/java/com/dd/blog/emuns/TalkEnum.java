package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DD
 * @date 2022/4/5 20:36
 */

@Getter
@AllArgsConstructor
public enum TalkEnum {

    PUBLIC(1, "公开");

    private final Integer status;
    private final String desc;
}
