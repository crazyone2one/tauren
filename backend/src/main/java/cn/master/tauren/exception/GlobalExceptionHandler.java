package cn.master.tauren.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * @author Created by 11's papa on 11/29/2024
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundException(NoHandlerFoundException nfe, WebRequest req) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("error", "No handler found for this request");
        map.put("details", req.getDescription(false));
        map.put("message", nfe.getMessage());
        map.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
