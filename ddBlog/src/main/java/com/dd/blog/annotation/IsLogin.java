package com.dd.blog.annotation;

import java.lang.annotation.*;

/**
 * 判断是否登录
 */

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface IsLogin {
}
