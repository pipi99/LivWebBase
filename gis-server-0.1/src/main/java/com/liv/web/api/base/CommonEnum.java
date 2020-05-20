package com.liv.web.api.base;

import com.liv.web.api.exception.IBaseErrorInfo;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.base
 * @Description: 返回结果枚举
 * @date 2020.3.26  11:34
 * @email 453826286@qq.com
 */
public enum CommonEnum implements IBaseErrorInfo {

    SUCCESS("成功!", 200),
    INTERNAL_SERVER_ERROR("服务器内部错误!", 500),
    BAD_REQUEST("非法参数!",500),
    NULL_POINT("空指针!", 500);


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