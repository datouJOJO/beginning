package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DD
 * @about 用户类型枚举
 * @date 2022/5/8 14:15
 */

@AllArgsConstructor
@Getter
public enum UserAreaEnum {

    /**
     * 用户
     */
    USER(1, "用户"),
    /**
     * 游客
     */
    VISITOR(2, "游客");

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;

    /**
     * 获取用户区域类型
     *
     * @param type 类型
     * @return {@link UserAreaEnum} 用户区域类型枚举
     */
    public static UserAreaEnum getUserAreaType(Integer type) {
        for (UserAreaEnum value : UserAreaEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
