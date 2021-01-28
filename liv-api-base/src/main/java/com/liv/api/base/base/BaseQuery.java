package com.liv.api.base.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.base
 * @Description: 基础查询类
 * @date 2020.7.21  10:54
 * @email 453826286@qq.com
 */
public interface BaseQuery<T> {

    /**组装查询条件*/
    public QueryWrapper<T> getQueryWrapper();
}
