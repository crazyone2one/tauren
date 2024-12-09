package cn.master.tauren.exception;

import cn.master.tauren.constants.ResultCode;

import java.util.Optional;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
public abstract class AbstractException extends RuntimeException {
    private final Integer code;
    private final String message;

    public AbstractException(ResultCode resultCode, String message, Throwable throwable) {
        super(message, throwable);
        this.code = resultCode.getCode();
        this.message = Optional.ofNullable(message).orElse(resultCode.getMessage());
    }
}
