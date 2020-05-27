package com.liv.api.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.auth.dao.RoleMapper;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.domainmodel.PermissionDO;
import com.liv.api.auth.service.PermissionService;
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
@Service("apiPermissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * @Author: LiV
     * @Date: 2020.5.25 15:45
     * @Description: 查询角色权限
     **/
    @Override
    public List<PermissionDO> findRolePermissions(String roleName) throws Exception {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq("ROLE_NAME",roleName);
        List<Role> roles = roleMapper.selectList(qw);
        if(roles==null||roles.size()==0){
            throw new Exception("角色"+roleName+"不存在！");
        }

        return roleMapper.findPermissionAndResources(roles.get(0).getRoleId());
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.25 15:45
     * @Description: 查询用户权限
     **/
    @Override
    public List<PermissionDO> findUserPermissions(Long userId) throws Exception {

        return userMapper.findPermissionAndResources(userId);
    }
}
