package com.liv.api.base.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.base
 * @Description: 基础 bean
 * @date 2020.6.9  10:03
 * @email 453826286@qq.com
 */
public abstract class BaseBean<T> extends Page<T> {

    /**获取分页查询信息*/
    public IPage<T> getPage() {
        Page<T> page = new Page<>(this.getCurrent(),this.getSize());
        return page;
    }

    /**组装查询条件*/
    protected abstract QueryWrapper<T> getQueryWrapper();
}
