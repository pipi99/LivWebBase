package com.liv.api.base.dicttype;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseBean;
import com.liv.api.base.base.BaseQuery;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.dict
 * @Description: 数据字典类型实体
 * @date 2020.6.9  14:26
 * @email 453826286@qq.com
 */
public class DictTypeQuery extends DictType implements BaseQuery<DictType> {
    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<DictType> getQueryWrapper() {
        QueryWrapper<DictType> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(this.getDTypeName())){
            qw.like("D_TYPE_NAME",this.getDTypeName());
        }
        if(StringUtils.isNotEmpty(this.getDType())){
            qw.like("D_TYPE",this.getDType());
        }
        return qw;
    }
}
