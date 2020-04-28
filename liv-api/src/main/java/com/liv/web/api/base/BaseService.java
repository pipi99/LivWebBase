package com.liv.web.api.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.base
 * @Description: Service操作基类
 * @date 2020.4.13  17:18
 * @email 453826286@qq.com
 */
@Component
public class BaseService<M extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>, T> extends ServiceImpl<M,T> {
    /**
     * @Author: LiV
     * @Date: 2020.4.16 10:09
     * @Description: service调用  mapper().XXX
     **/
    protected M mapper(){
        return (M)getBaseMapper();
    }

}
