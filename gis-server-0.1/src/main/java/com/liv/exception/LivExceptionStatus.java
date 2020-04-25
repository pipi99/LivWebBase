package com.liv.exception;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: 异常状态枚举
 * @date 2020.3.26  11:07
 * @email 453826286@qq.com
 */
public enum LivExceptionStatus implements IBaseErrorInfo{
    CommonException("发生异常了。。", -1000);

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
