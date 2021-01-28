package com.liv.api.auth.shiro.permission;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.liv.api.auth.dao.datamodel.Actions;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.domainmodel.PermissionDO;
import com.liv.api.auth.service.MenuService;
import com.liv.api.auth.service.PermissionService;
import com.liv.api.auth.service.RoleService;
import com.liv.api.auth.shiro.ShiroRoles;
import com.liv.api.auth.shiro.cache.CacheFactory;
import com.liv.api.auth.utils.ApiAuthUtils;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.base.utils.LivContextUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
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
 *
 * ====
 * 拦截浏览器url访问菜单路径
 * ====
 *
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
        UserVO u = ApiAuthUtils.getInstance().getCurrentUser().getUser();
        String userPermissionsRoleString = u.getUserName();

        permissions= new ArrayList<>();

        //超级管理员
        if(ShiroRoles.SUPERMAN.equalsIgnoreCase(roleString)||ShiroRoles.SYSMAN.equalsIgnoreCase(roleString)) {
            //AllPermission表示具有所有的权限
            //同时具有授权管理员角色的权限及其他超级权限
            permissions.add(new AllPermission());

        //获取单个用户的权限
        }else if(roleString.equals(userPermissionsRoleString)){
            //用户权限列表
            PermissionService permissionService = LivContextUtils.getBean("apiPermissionService", PermissionService.class);
            List<PermissionDO> userPermissions = permissionService.findUserPermissions(u.getUserId());
            //把角色拥有的权限赋拼接通配符赋予角色
            for (int i = 0; i < userPermissions.size(); i++) {
                PermissionDO userPermission = userPermissions.get(i);
                //用菜单的url作为通配符号
                permissions.addAll(createBitPermission(userPermission));
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
                permissions.addAll(createBitPermission(rolePermission));
            }
        }

        //添加缓存
        CacheFactory.getCache(CacheFactory.ROLE_PERMISSION_CACHE).put(roleString,permissions);

        return permissions;
    }

    private List<BitPermission> createBitPermission(PermissionDO permission){
        List<BitPermission> bitPermissions = Lists.newArrayList();
        //用菜单的url作为通配符号
        if(StringUtils.isNotEmpty(permission.getMUrl())){
            BitPermission BitPermission = createBitPermission(permission.getMUrl());
            bitPermissions.add(BitPermission);
        }

        //按钮的url
//        List<Actions> actions = permission.getActions();
//        if(actions!=null&&actions.size()>0){
//            for (int i = 0; i < actions.size(); i++) {
//                Actions actions1 = actions.get(i);
//                if(StringUtils.isNotEmpty(actions1.getActionUrl())){
//                    bitPermissions.add(createBitPermission(actions1.getActionUrl()));
//                }
//            }
//        }
        return bitPermissions;
    }

    private BitPermission createBitPermission(String url){
        //用菜单的url作为通配符号
        String resourceFlag = ApiAuthUtils.getInstance().getPermissionStrByUrl(url);
        /**
         * 这里如果在后台配置了关联关系，说明用户对此资源（菜单、按钮）具有访问权限
         * 给予 1023（1111111111） 10个权限。  （其实1个1 就够）
         * 1、MenuService 中把用户需要授权才能访问的资源添加到 PermissionsFilter 进行拦截
         * 2、此处给用户授权
         * 3、在 PermissionsFilter 中 根据拦截到的 url 进行权限判断
         * */
        BitPermission BitPermission = new BitPermission("+"+resourceFlag+"+1");
        return BitPermission;
    }

}