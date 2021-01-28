package com.liv.auth.dao.datamodel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseQuery;
import org.apache.commons.lang3.StringUtils;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.dict
 * @Description: 数据字典实体
 * @date 2020.6.9  14:26
 * @email 453826286@qq.com
 */
public class AppQuery extends App implements BaseQuery<App> {
    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<App> getQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(this.getAppName())){
            qw.like("APP_NAME",this.getAppName());
        }
        return qw;
    }
}
