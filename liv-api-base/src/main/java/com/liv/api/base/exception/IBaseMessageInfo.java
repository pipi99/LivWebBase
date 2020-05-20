package com.liv.api.base.exception;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: 异常信息描述接口
 * @date 2020.3.26  11:19
 * @email 453826286@qq.com
 */
public interface IBaseMessageInfo {
    /** 错误码*/
    public int getCode();

    /** 错误描述*/
    public String getMessage();
}
