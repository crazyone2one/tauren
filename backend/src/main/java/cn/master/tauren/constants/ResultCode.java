package cn.master.tauren.constants;

import lombok.Getter;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
@Getter
public enum ResultCode {
    SUCCESS(200, "成功"),
    //FAIL(400, "失败"),//失败
    BAD_REQUEST(400, "Bad Request"),
    //未认证
    UNAUTHORIZED(401, "认证失败"),
    //接口不存在
    NOT_FOUND(404, "接口不存在"),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500, "系统繁忙"),
    METHOD_NOT_ALLOWED(405, "方法不被允许"),

    /*参数错误:1001-1999*/
    PARAMS_IS_INVALID(1001, "参数无效"),
    PARAMS_IS_BLANK(1002, "参数为空"),
    /*用户错误2001-2999*/
    TOKEN_ERROR(2000, "Refresh token does not exist"),
    TOKEN_ERROR_02(2002, "Token is null"),
    TOKEN_ERROR_03(2003, "Refresh token does not exist"),
    TOKEN_ERROR_04(2004, "Refresh token was expired");

    private final Integer code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
