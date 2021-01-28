package com.liv.api.base.base;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.base
 * @Description: 基础 bean
 * @date 2020.6.9  10:03
 * @email 453826286@qq.com
 */
public class BaseBean<T> extends Page<T> {
    protected transient IPage<T> page;
    protected transient java.util.List<T> records;
    protected transient long total;
    protected transient long size;
    protected transient long current;
    protected transient java.lang.String[] ascs;
    protected transient java.lang.String[] descs;
    protected transient boolean optimizeCountSql;
    protected transient boolean isSearchCount;
    protected transient java.util.List<com.baomidou.mybatisplus.core.metadata.OrderItem> orders;
    protected transient boolean hitCount;
    protected transient java.lang.String countId;
    protected transient java.lang.Long maxLimit;

//    /**获取分页查询信息*/
    public IPage<T> getPage() {
        Page<T> page = new Page<>(this.getCurrent(),this.getSize());
        return page;
    }

    public java.util.List<T> getRecords() {
        return this.records;
    }

    public com.baomidou.mybatisplus.extension.plugins.pagination.Page<T> setRecords(java.util.List<T> records) {
        this.records = records;
        return this;
    }

}
