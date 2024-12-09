package cn.master.tauren.ret;

import cn.master.tauren.constants.ResultCode;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
@Data
public class ResultHolder implements Serializable {
    @Serial
    private static final long serialVersionUID = 6308315887056661996L;
    private Integer code = ResultCode.SUCCESS.getCode();
    // 描述信息，一般是错误信息
    private String message;
    // 详细描述信息, 如有异常，这里是详细日志
    private Object messageDetail;
    // 返回数据
    private Object data = "";

    public ResultHolder() {
    }

    public ResultHolder(Object data) {
        this.data = data;
    }

    public ResultHolder(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResultHolder(Object data, String msg) {
        this.data = data;
        this.message = msg;
    }

    public ResultHolder(int code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ResultHolder(int code, String msg, Object messageDetail, Object data) {
        this.code = code;
        this.message = msg;
        this.messageDetail = messageDetail;
        this.data = data;
    }

    public static ResultHolder success(Object obj) {
        return new ResultHolder(obj);
    }

    public static ResultHolder success(Object obj, String message) {
        return new ResultHolder(obj, message);
    }

    public static ResultHolder error(int code, String message) {
        return new ResultHolder(code, message, null, null);
    }

    public static ResultHolder error(String message, String messageDetail) {
        return new ResultHolder(-1, message, messageDetail, null);
    }

    public static ResultHolder error(int code, String message, Object messageDetail) {
        return new ResultHolder(code, message, messageDetail, null);
    }

    /**
     * 用于特殊情况，比如接口可正常返回，http状态码200，但是需要页面提示错误信息的情况
     *
     * @param code    自定义 code
     * @param message 给前端返回的 message
     * @return
     */
    public static ResultHolder successCodeErrorInfo(int code, String message) {
        return new ResultHolder(code, message, null, null);
    }
}
