package com.dd.blog.annotation;

import java.lang.annotation.*;

/**
 * @author DD
 * @about 用户账号控制器
 * @date 2022/4/19 20:34
 */

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount();
}
