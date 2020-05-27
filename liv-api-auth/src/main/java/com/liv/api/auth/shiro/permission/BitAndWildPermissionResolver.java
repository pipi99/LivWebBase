package com.liv.api.auth.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.shiro.permission
 * @Description: BitAndWildPermissionResolver
 * @date 2020.5.21  18:32
 * @email 453826286@qq.com
 */
public class BitAndWildPermissionResolver implements PermissionResolver {
    @Override
    public Permission resolvePermission(String permissionString) {
        if(permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
