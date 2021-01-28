package com.liv.auth.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.auth.dao.datamodel.App;
import io.netty.handler.ipfilter.IpFilterRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroupMapper extends BaseMapper<Groups> {
    /**
     * @Author: LiV
     * @Date: 2020.7.8 18:57
     * @Description: 查询用户 用户组和角色
     **/
    public IPage<Groups> findGroupRole(IPage<Groups> page, @Param(Constants.WRAPPER) Wrapper<Groups> queryWrapper);

}