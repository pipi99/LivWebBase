package com.liv.sp.dao.datamodel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseQuery;
import org.apache.commons.lang3.StringUtils;

/**
 * @author LiV
 * @Title: 查询工具类
 * @Package com.liv.sp.dao.datamodel
 * @Description:
 * @date 2020.12.11  17:41
 * @email 453826286@qq.com
 */
public class SpQuery extends Sp implements BaseQuery<Sp> {
    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<Sp> getQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(this.getName())){
            qw.like("NAME",this.getName());
        }
        return qw;
    }
}
