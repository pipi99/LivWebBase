package com.liv.base;

import com.alibaba.fastjson.JSONObject;
import com.liv.exception.IBaseErrorInfo;
import lombok.Data;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.base
 * @Description: 响应实体
 * @date 2020.3.26  11:31
 * @email 453826286@qq.com
 */
@Data
public class ResultBody {
    /**
     * 响应代码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultBody() {
    }

    public ResultBody(IBaseErrorInfo errorInfo) {
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMessage();
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultBody success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.SUCCESS.getCode());
        rb.setMessage(CommonEnum.SUCCESS.getMessage());
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(IBaseErrorInfo errorInfo) {
        ResultBody rb = new ResultBody();
        rb.setCode(errorInfo.getCode());
        rb.setMessage(errorInfo.getMessage());
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(int code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error( String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(-1);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }
}
