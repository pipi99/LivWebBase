package com.liv.api.auth.service.impl;

import com.google.common.collect.Lists;
import com.liv.api.auth.dao.RoleMapper;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.service.RoleService;
import com.liv.api.base.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("apiRoleService")
public class RoleServiceImpl extends BaseService<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @Author: LiV
     * @Date: 2020.5.26 15:57
     * @Description: 获取当前用户的角色
     **/
    @Override
    public List<Role> getUserRoles(Long userId) {
        return this.mapper.findByUserId(userId);
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.26 15:58
     * @Description: 获取当前用户的用户组的角色
     **/
    @Override
    public List<Role> getGroupRoles(Long userId) {
        List<Groups> groups = userMapper.findGroups(userId);

        if(groups.size()==0){
            return Lists.newArrayList();
        }

        List<Long> groupids = Lists.newArrayList();
        for (int i = 0; i < groups.size(); i++) {
            groupids.add(groups.get(i).getGroupId());
        }
        return this.mapper.findByGroupIds(groupids);
    }
}
