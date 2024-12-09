package cn.master.tauren.exception;

import cn.master.tauren.constants.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenException extends AbstractException {
    public TokenException(ResultCode errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }
}
