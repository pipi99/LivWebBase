package com.liv.api.auth.exception;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: 自定义异常
 * @date 2020.3.26  11:02
 * @email 453826286@qq.com
 */
public class LivException extends RuntimeException {
    /**
     * 错误码
     */
    protected int code;
    /**
     * 错误信息
     */
    protected String message;

    public LivException() {
        super();
    }

    public LivException(IBaseErrorInfo exceptionInfo) {
        super(exceptionInfo.getMessage());
        this.code = exceptionInfo.getCode();
        this.message = exceptionInfo.getMessage();
    }

    public LivException(IBaseErrorInfo exceptionInfo, Throwable cause) {
        super(exceptionInfo.getMessage(), cause);
        this.code = exceptionInfo.getCode();
        this.message = exceptionInfo.getMessage();
    }

    public LivException(String message) {
        super(message);
        this.message = message;
    }

    public LivException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public LivException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
