package com.liv.auth.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.GroupsQuery;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.auth.dao.datamodel.App;
import org.apache.ibatis.annotations.Param;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface GroupService extends IService<Groups>{
    /**
     * @Author: LiV
     * @Date: 2020.7.8 18:57
     * @Description: 查询用户 用户组和角色
     **/
    public IPage<Groups> findGroupRole(GroupsQuery query);
    public void add(Groups groups);
    public void update(Groups groups);
    public void delete(Long id);

}
