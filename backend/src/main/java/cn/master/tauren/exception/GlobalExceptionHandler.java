package cn.master.tauren.exception;

import cn.master.tauren.constants.ResultCode;
import cn.master.tauren.ret.ResultHolder;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Created by 11's papa on 11/29/2024
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultHolder handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResultHolder.error(ResultCode.BAD_REQUEST.getCode(), ResultCode.BAD_REQUEST.getMessage(), errors);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResultHolder> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResultHolder.error(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> noHandlerFoundException(NoHandlerFoundException nfe, WebRequest req) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("error", "No handler found for this request");
        map.put("details", req.getDescription(false));
        map.put("message", nfe.getMessage());
        map.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AbstractException.class})
    public ResultHolder handleAbstractException(HttpServletRequest request, AbstractException ex) {
        log.error("[{}] {} [ex] {}", request.getMethod(), request.getRequestURI(), ex.toString());
        return ResultHolder.error(null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        getStackTraceAsString(exception);
        return switch (exception) {
            case BadCredentialsException ignored -> ResponseEntity.status(401)
                    .body(ResultHolder.error(ResultCode.UNAUTHORIZED.getCode(), "http_result_unauthorized", exception.getMessage()));
            case AccountStatusException ignored -> ResponseEntity.status(403)
                    .body(Map.of("code", 403, "message", exception.getMessage(), "description", "The account is locked"));
            case AccessDeniedException ignored -> ResponseEntity.status(403)
                    //.body(Map.of("code", 403, "message", exception.getMessage(), "description", "You are not authorized to access this resource"));
                    .body(ResultHolder.error(ResultCode.FORBIDDEN.getCode(), exception.getMessage(), "You are not authorized to access this resource"));
            case ExpiredJwtException ignored -> ResponseEntity.status(401)
                    //.body(Map.of("code", 401, "message", exception.getMessage(), "description", "The JWT token has expired"));
                    .body(ResultHolder.error(ResultCode.UNAUTHORIZED.getCode(), exception.getMessage(), "The JWT token has expired"));
            default -> ResponseEntity.status(500)
                    .body(ResultHolder.error(ResultCode.PARAMS_IS_INVALID.getCode(),
                            ResultCode.PARAMS_IS_INVALID.getMessage(), exception.getMessage()));
        };
    }

    public static void getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        //sw.toString();
    }
}
