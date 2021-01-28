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
import java.io.Serializable;

public class GroupsQuery extends Groups implements BaseQuery<Groups> {

    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<Groups> getQueryWrapper() {
        QueryWrapper<Groups> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(this.getGroupName())){
            qw.like("GROUP_NAME",this.getGroupName());
        }
        return qw;
    }
}