package com.liv.web.api.web.api.base.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.base
 * @Description: Service操作基类
 * @date 2020.4.13  17:18
 * @email 453826286@qq.com
 */
public class BaseService<M extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>, T> extends ServiceImpl<M,T> {
    /**
     * @Author: LiV
     * @Date: 2020.4.16 10:09
     * @Description: service调用
     **/
    protected M mapper;

    @PostConstruct
    private void mapper(){
        this.mapper = (M)getBaseMapper();
    }

}
