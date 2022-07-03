package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DD
 * @about
 * @date 2022/4/25 21:31
 */

@Getter
@AllArgsConstructor
public enum RoleEnum {

    USER(2, "用户", "user");
    /**
     * 角色id
     */
    private final Integer roleId;
    /**
     * 描述
     */
    private final String desc;
    /**
     * 权限标签
     */
    private final String label;
}
