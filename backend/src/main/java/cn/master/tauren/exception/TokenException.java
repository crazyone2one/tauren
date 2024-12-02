package cn.master.tauren.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenException extends RuntimeException {
    public TokenException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
