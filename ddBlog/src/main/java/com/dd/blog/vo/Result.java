package com.dd.blog.vo;

import lombok.Data;
import static com.dd.blog.emuns.StatusCodeEnum.*;

/**
 * 接口返回数据
 * @author DD
 * @date 2022/4/3 21:52
 */

@Data
public class Result<T> {
    /**
     * 返回状态
     */
    private Boolean flag;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public static <T> Result ok(T data, String message) {
        return buildResult(true, data, SUCCESS.getCode(), message);
    }

    public static <T> Result ok(T data) {
        return buildResult(true, data, SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public static <T> Result ok() {
        return buildResult(true, null, SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public static <T> Result fail(String msg) {
        return buildResult(false, null, FAIL.getCode(), msg);
    }

    public static <T> Result fail(int code, String msg) {
        return buildResult(false, null, code, msg);
    }

    private static <T> Result buildResult(Boolean flag, T data, Integer code, String message) {
        Result<T> result = new Result<>();
        result.setFlag(flag);
        result.setData(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
