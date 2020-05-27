package com.liv.api.auth.shiro.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.domainmodel.PermissionDO;
import com.liv.api.auth.service.MenuService;
import com.liv.api.auth.service.PermissionService;
import com.liv.api.auth.service.RoleService;
import com.liv.api.auth.shiro.ShiroRoles;
import com.liv.api.auth.shiro.cache.CacheFactory;
import com.liv.api.auth.utils.ApiAuthUtils;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.base.utils.LivContextUtils;
import lombok.SneakyThrows;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.AllPermission;
import org.apache.shiro.authz.permission.RolePermissionResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.shiro.permission
 * @Description: 角色权限解析器,获取角色拥有的权限 (含用户权限)
 * @date 2020.5.21  16:43
 * @email 453826286@qq.com
 */
public class LivRolePermissionResolver implements RolePermissionResolver {
    //实现通过角色解析权限的过程
    @SneakyThrows
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {

        List<Permission> permissions = (List)CacheFactory.getCache(CacheFactory.ROLE_PERMISSION_CACHE).get(roleString);
        if(permissions!=null){
            return permissions;
        }

        //支持用户权限，此处 “用户名” 作为用户权限的角色变种名称
        UserVO u = ApiAuthUtils.getCurrentUser().getUser();
        String userPermissionsRoleString = u.getUserName();

        permissions= new ArrayList<>();

        //超级管理员
        if(ShiroRoles.SUPERMAN.equalsIgnoreCase(roleString)) {
            //AllPermission表示具有所有的权限
            //同时具有授权管理员角色的权限及其他超级权限
            permissions.add(new AllPermission());

        //系统管理员//拥有 system 前缀的权限
        }else if(ShiroRoles.SYSMAN.equalsIgnoreCase(roleString)) {
            //管理员不具备授权管理员权限
            permissions.add(new AllPermission());


        }else if(ShiroRoles.ANONYMOUS.equalsIgnoreCase(roleString)) {
            //TODO 任意角色权限  // 拥有不必授权可访问的菜单权限
            MenuService menuService = LivContextUtils.getBean("apiMenuService", MenuService.class);
            QueryWrapper<Menu> queryWrapper = new QueryWrapper();
            queryWrapper.eq("IS_SHARE","1");
            List<Menu> menus = menuService.list(queryWrapper);
            //把角色拥有的权限赋拼接通配符赋予角色
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);

                //用菜单的url作为通配符号
                String resourceFlag = ApiAuthUtils.getInstance(LivContextUtils.getRequest()).getPermissionStrByUrl(menu.getMUrl());
                BitPermission BitPermission = new BitPermission("+"+resourceFlag+"+1023");
                permissions.add(BitPermission);
            }

            //用户权限（用户名 模拟角色）
        }else if(roleString.equals(userPermissionsRoleString)){
            //用户权限列表
            PermissionService permissionService = LivContextUtils.getBean("apiPermissionService", PermissionService.class);
            List<PermissionDO> userPermissions = permissionService.findUserPermissions(u.getUserId());

            //把角色拥有的权限赋拼接通配符赋予角色
            for (int i = 0; i < userPermissions.size(); i++) {
                PermissionDO userPermission = userPermissions.get(i);

                //用菜单的url作为通配符号
                String resourceFlag = ApiAuthUtils.getInstance(LivContextUtils.getRequest()).getPermissionStrByUrl(userPermission.getMUrl());
                BitPermission BitPermission = new BitPermission("+"+resourceFlag+"+"+userPermission.getPermission());
                permissions.add(BitPermission);
            }
        //角色权限
        }else{
            //角色权限列表
            PermissionService permissionService = LivContextUtils.getBean("apiPermissionService", PermissionService.class);
            List<PermissionDO> rolePermissionList = permissionService.findRolePermissions(roleString);

            //把角色拥有的权限赋拼接通配符赋予角色
            for (int i = 0; i < rolePermissionList.size(); i++) {
                PermissionDO rolePermission = rolePermissionList.get(i);

                //用菜单的url作为通配符号
                String resourceFlag = ApiAuthUtils.getInstance(LivContextUtils.getRequest()).getPermissionStrByUrl(rolePermission.getMUrl());
                BitPermission BitPermission = new BitPermission("+"+resourceFlag+"+"+rolePermission.getPermission());
                permissions.add(BitPermission);
            }
        }

        //添加缓存
        CacheFactory.getCache(CacheFactory.ROLE_PERMISSION_CACHE).put(roleString,permissions);

        return permissions;
    }

}