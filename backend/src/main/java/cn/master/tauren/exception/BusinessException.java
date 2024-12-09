package cn.master.tauren.exception;

import cn.master.tauren.constants.ResultCode;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
public class BusinessException extends AbstractException {

    public BusinessException(ResultCode errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

    public BusinessException(String message) {
        super(ResultCode.INTERNAL_SERVER_ERROR, message, null);
    }
}
