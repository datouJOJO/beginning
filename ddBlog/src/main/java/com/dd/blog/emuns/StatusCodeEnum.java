package com.dd.blog.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 接口状态码枚举
 * @author DD
 * @date 2022/04/03
 */
//生成get方法
@Getter
//使用后添加一个构造函数，该构造函数含有所有已声明字段属性参数
@AllArgsConstructor
public enum StatusCodeEnum {
    /**
     * 成功
     */
    SUCCESS(20000,
            "操作成功"),
    /**
     * 失败
     */
    FAIL(51000, "操作失败"),
    /**
     * 上传失败
     */
    UPLOAD_FAIL(60001, "上传失败"),
    /**
     * token失效
     */
    TOKEN_FAIL(60002, "token超时失效 请重新登录"),
    /**
     *
     */
    TOKEN_CHECK_FAIL(60003, "token校验失败 请检查自己的信息是否泄露"),
    /**
     * token为空
     */
    TOKEN_IS_BLANK(60004, "token格式不正确");
    private final Integer code;
    private final String message;
}
