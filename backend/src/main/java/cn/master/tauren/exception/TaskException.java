package cn.master.tauren.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * @author Created by 11's papa on 12/05/2024
 **/
@Getter
public class TaskException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Code code;

    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }

    public TaskException(String msg, Code code, Exception nestedEx) {
        super(msg, nestedEx);
        this.code = code;
    }

    public enum Code {
        TASK_EXISTS, NO_TASK_EXISTS, TASK_ALREADY_STARTED, UNKNOWN, CONFIG_ERROR, TASK_NODE_NOT_AVAILABLE
    }
}
