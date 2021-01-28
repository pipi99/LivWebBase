package com.liv.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liv.api.auth.dao.GroupRoleMapper;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.GroupsQuery;
import com.liv.api.auth.dao.datamodel.GroupRole;
import com.liv.api.base.base.BaseService;
import com.liv.auth.dao.GroupMapper;
import com.liv.auth.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("GroupService")
public class GroupServiceImpl extends BaseService<GroupMapper, Groups> implements GroupService {

    @Autowired
    private GroupRoleMapper groupRoleMapper;
    /**
     * @param query
     * @Author: LiV
     * @Date: 2020.7.8 18:57
     * @Description: 查询用户 用户组和角色
     */
    @Override
    public IPage<Groups> findGroupRole(GroupsQuery query) {
        return this.mapper.findGroupRole(query.getPage(),query.getQueryWrapper());
    }

    @Override
    public void add(Groups groups) {
        this.mapper.insert(groups);

        addrole(groups);
    }

    @Override
    public void update(Groups groups) {
        this.mapper.updateById(groups);

        QueryWrapper qw = new QueryWrapper();
        qw.eq("GROUP_ID",groups.getGroupId());
        //删除用户组关联关系
        groupRoleMapper.delete(qw);

        addrole(groups);
    }

    @Override
    public void delete(Long id) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("GROUP_ID",id);

        //删除用户组关联关系
        groupRoleMapper.delete(qw);
        this.mapper.deleteById(id);
    }

    private void addrole(Groups groups){
        //新增角色关联关系
        Long[] roleIds= groups.getRoleIds();
        if(roleIds!=null){
            for (int i = 0; i <roleIds.length ; i++) {
                GroupRole gr = new GroupRole();
                gr.setRoleId(roleIds[i]);
                gr.setGroupId(groups.getGroupId());
                groupRoleMapper.insert(gr);
            }
        }
    }
}
