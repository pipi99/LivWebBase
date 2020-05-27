package com.liv.api.base.exception;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.exception
 * @Description: 异常处理
 * @date 2020.5.27  20:50
 * @email 453826286@qq.com
 */
public class LivCommonException implements IBaseMessageInfo {
    private int code;
    private String message;
    public LivCommonException(int code,String message){
        this.code = code;
        this.message = message;
    }
    /**
     * 错误码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 错误描述
     */
    @Override
    public String getMessage() {
        return message;
    }
}
