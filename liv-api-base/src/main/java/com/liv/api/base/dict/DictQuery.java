package com.liv.api.base.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseBean;
import com.liv.api.base.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.dict
 * @Description: 数据字典实体
 * @date 2020.6.9  14:26
 * @email 453826286@qq.com
 */
public class DictQuery extends Dict implements BaseQuery<Dict> {

    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<Dict> getQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(this.getDType())){
            qw.eq("D_TYPE",this.getDType());
        }
        if(StringUtils.isNotEmpty(this.getDValue())){
            qw.like("D_VALUE",this.getDValue());
        }
        if(StringUtils.isNotEmpty(this.getDCode())){
            qw.like("D_CODE",this.getDCode());
        }
        return qw;
    }
}
