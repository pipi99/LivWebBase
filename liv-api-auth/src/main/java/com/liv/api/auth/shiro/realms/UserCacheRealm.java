package com.liv.api.auth.shiro.realms;

import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.domainmodel.UserDO;
import com.liv.api.auth.service.RoleService;
import com.liv.api.auth.shiro.ShiroRoles;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.bizc.login
 * @Description: 用户登录验证
 * @date 2020.4.19  18:27
 * @email 453826286@qq.com
 */
public class UserCacheRealm extends AuthorizingRealm {
    public  static final String REAL_NAME = "livUserCacheRealm";
    @Override
    public String getName() {
        return REAL_NAME;
    }
    /**
     * 注意此处需要添加@Lazy注解，否则UserService缓存注解、事务注解不生效
     */
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //根据用户名查找角色，请根据需求实现
        String username = (String) principals.getPrimaryPrincipal();

        //获取当前用户
        UserDO authorizationInfo = new UserDO();
        UserVO user = new UserVO();
        BeanUtils.copyProperties(userService.findByUserName(username),user);
        authorizationInfo.setUser(user);

        /**用户角色**/
        List<Role> roleList = roleService.getUserRoles(user.getUserId());
        for (int i = 0; i <roleList.size() ; i++) {
            authorizationInfo.addRole(roleList.get(i).getRoleName());
        }

        /**用户组角色**/
        roleList = roleService.getGroupRoles(user.getUserId());
        for (int i = 0; i <roleList.size() ; i++) {
            authorizationInfo.addRole(roleList.get(i).getRoleName());
        }

        /**用户权限(本用户名作为角色)**/
        //用户名 作为本用户唯一角色，支持用户权限
        authorizationInfo.addRole(user.getUserName());

        //**** 赋予任意角色在初始登录需要获取用户信息的情况下，可以使用此角色，以便shiro缓存用户信息
        //见 UserServiceImpl.getCurUser()
        authorizationInfo.addRole(ShiroRoles.ANONYMOUS);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        User user = userService.findByUserName(username);
        if(user == null) {
            throw new UnknownAccountException("账号不存在!");//没找到帐号
        }

        /**
         若密码验证成功，第一个参数将会作为user.getPrincipals().getPrimaryPrincipal() 返回。
         此处设置为 user则返回对象，设置为username则返回用户名称
         **/
        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
        ai.setCredentialsSalt(ByteSourceUtils.bytes(user.getCredentialsSalt())); //盐是用户名+随机数
        return ai;
    }

    @Override
    public void doClearCache(org.apache.shiro.subject.PrincipalCollection principals) {
        super.doClearCache(principals);
    }

}
