package com.liv.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseBean;
import com.liv.api.base.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author Liv
 * @since 2020-05-21
 */
public class OrganQuery extends Organ implements BaseQuery<Organ> {
    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<Organ> getQueryWrapper() {
        QueryWrapper<Organ> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(this.getOrganName())){
            qw.like("ORGAN_NAME",this.getOrganName());
        }
        if(StringUtils.isNotEmpty(this.getOrganLevel())){
            qw.eq("ORGAN_LEVEL",this.getOrganLevel());
        }
        if(StringUtils.isNotEmpty(this.getOrganType())){
            qw.eq("ORGAN_TYPE",this.getOrganType());
        }
        return qw;
    }
}
