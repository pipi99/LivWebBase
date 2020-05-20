package com.liv.web.api.web.api.base.base;


import com.liv.web.api.web.api.base.exception.IBaseMessageInfo;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.base
 * @Description: 返回结果枚举
 * @date 2020.3.26  11:34
 * @email 453826286@qq.com
 */
public enum CommonEnum implements IBaseMessageInfo {

    SUCCESS("成功!", 200);


    private String message;
    private int code;

    private CommonEnum (String message, int code) {
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