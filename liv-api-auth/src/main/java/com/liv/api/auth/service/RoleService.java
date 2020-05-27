package com.liv.api.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.domainmodel.PermissionDO;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface RoleService extends IService<Role>{
    List<Role> getUserRoles(Long userId);
    List<Role> getGroupRoles(Long userId);
}
