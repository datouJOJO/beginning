package com.dd.blog.exception;

import com.dd.blog.emuns.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.dd.blog.emuns.StatusCodeEnum.*;

/**
 * @author DD
 * @about
 * @date 2022/4/16 11:09
 */

@Getter
@AllArgsConstructor
public class BizException extends RuntimeException{

    private Integer code = FAIL.getCode();
    private final String msg;

    public BizException(String msg) {
        this.msg = msg;
    }
    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.msg = statusCodeEnum.getMessage();
    }
}
