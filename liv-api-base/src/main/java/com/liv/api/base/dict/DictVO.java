package com.liv.api.base.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.dict
 * @Description: 数据字典实体
 * @date 2020.6.9  14:26
 * @email 453826286@qq.com
 */
public class DictVO {

    private String dType;
    private String dValue;
    private String dCode;
    private String parentCode;

    private List<DictVO> children;

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getdValue() {
        return dValue;
    }

    public void setdValue(String dValue) {
        this.dValue = dValue;
    }

    public String getdCode() {
        return dCode;
    }

    public void setdCode(String dCode) {
        this.dCode = dCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
    public List<DictVO> getChildren() {
        if(children==null||children.size()==0){
            return null;
        }
        return children;
    }

    public void setChildren(List<DictVO> children) {
        this.children = children;
    }


}
