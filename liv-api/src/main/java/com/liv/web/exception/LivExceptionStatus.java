package com.liv.web.exception;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: 异常状态枚举
 * @date 2020.3.26  11:07
 * @email 453826286@qq.com
 */
public enum LivExceptionStatus implements IBaseMessageInfo {

    UNAUTHORIZED("未经许可的!", 401),
    FORBIDDEN("禁止访问的!", 403),
    NOT_FOUND("无效路径",404),

    VALIDATE_FAIL("参数校验异常!", 10000),
    BAD_REQUEST("错误的请求",10002),
    INTERNAL_SERVER_ERROR("服务器内部错误!", 500),
    NULL_POINT("空指针!", 500);

    private String message;
    private int code;

    private LivExceptionStatus (String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}
