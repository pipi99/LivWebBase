package com.liv.shiro.realms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.datamodel.User;
import com.liv.service.UserService;
import com.liv.utils.PasswordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.bizc.login
 * @Description: 用户登录验证
 * @date 2020.4.19  18:27
 * @email 453826286@qq.com
 */
public class UserRealm extends AuthorizingRealm {

    @Override
    public String getName() {
        return "livUserRealm";
    }
    /**
     * 注意此处需要添加@Lazy注解，否则UserService缓存注解、事务注解不生效
     */
    @Autowired
    @Lazy
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        User user = (User)principals.getPrimaryPrincipal();
        //根据用户名查找角色，请根据需求实现
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo =  new SimpleAuthorizationInfo();
        authorizationInfo.addRole("admin");

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        User user = userService.findByUserName(username);
        if(user == null) {
            throw new UnknownAccountException("账号不存在!");//没找到帐号
        }
        if("1".equals(user.getLocked())) {
            throw new LockedAccountException("账号已被锁定（10分钟）,请联系管理员，或者稍后再试!"); //帐号锁定
        }

        /**
         若密码验证成功，第一个参数将会作为user.getPrincipals().getPrimaryPrincipal() 返回。
         此处设置为 user则返回对象，设置为username则返回用户名称
         **/
        SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
        ai.setCredentialsSalt(ByteSourceUtils.bytes(user.getCredentialsSalt())); //盐是用户名+随机数
        return ai;
    }
}
