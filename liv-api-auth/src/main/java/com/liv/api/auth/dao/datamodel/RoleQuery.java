package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseBean;
import com.liv.api.base.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

public class RoleQuery extends Role implements BaseQuery<Role> {

    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<Role> getQueryWrapper() {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(this.getRoleName())){
            qw.like("ROLE_NAME",this.getRoleName());
        }
        if(StringUtils.isNotEmpty(this.getRoleAlias())){
            qw.like("ROLE_ALIAS",this.getRoleAlias());
        }
        return qw;
    }
}