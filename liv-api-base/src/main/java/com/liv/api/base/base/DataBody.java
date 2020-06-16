package com.liv.api.base.base;

import com.liv.api.base.exception.IBaseMessageInfo;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "DataBody",description = "统一返回结果实体")
public class DataBody {
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
    private Object data;

    public DataBody() {
    }

    public DataBody(IBaseMessageInfo errorInfo) {
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMessage();
    }

    /**
     * 成功
     *
     * @return
     */
    public static DataBody success() {
        return success("操作成功！");
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static DataBody success(Object data) {
        DataBody rb = new DataBody();
        rb.setCode(CommonEnum.SUCCESS.getCode());
        rb.setMessage(CommonEnum.SUCCESS.getMessage());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static DataBody error(IBaseMessageInfo errorInfo, Object data) {
        DataBody rb = new DataBody();
        rb.setCode(errorInfo.getCode());
        rb.setMessage(errorInfo.getMessage());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static DataBody error(IBaseMessageInfo errorInfo) {
        DataBody rb = new DataBody();
        rb.setCode(errorInfo.getCode());
        rb.setMessage(errorInfo.getMessage());
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static DataBody error(int code, String message) {
        DataBody rb = new DataBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static DataBody error(String data) {
        DataBody rb = new DataBody();
        rb.setCode(500);
        rb.setMessage("操作失败！");
        rb.setData(data);
        return rb;
    }
}
