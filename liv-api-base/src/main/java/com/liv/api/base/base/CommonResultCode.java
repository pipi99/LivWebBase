package com.liv.api.base.base;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.base
 * @Description: 返回结果枚举
 * @date 2020.3.26  11:34
 * @email 453826286@qq.com
 */
public enum CommonResultCode implements IBaseMessageInfo {

    SUCCESS("成功!", 200),
    INVALID ("参数错误!", 400),
    NO_PERMISSION("无权限!", 403),
    ERROR("服务器异常!", 500),
    DATA_QUERY_ERROR ("数据获取失败!", 501);

    private String message;
    private int code;

    private CommonResultCode (String message, int code) {
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