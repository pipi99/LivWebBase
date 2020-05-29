package com.liv.api.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.domainmodel.UserDO;
import com.liv.api.auth.service.RoleService;
import com.liv.api.auth.shiro.ShiroRoles;
import com.liv.api.auth.shiro.stateless.jwt.JwtUtil;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.shiro.cache.CacheFactory;
import com.liv.api.auth.shiro.realms.RetryLimitHashedCredentialsMatcher;
import com.liv.api.auth.utils.ApiAuthUtils;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.auth.utils.PasswordHelper;
import com.liv.api.base.base.BaseService;
import com.liv.api.base.utils.LivContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("apiUserService")
public class UserServiceImpl extends BaseService<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;
    /**
     * @Author: LiV
     * @Date: 2020.4.22 14:08
     * @Description: 用户登录，返回token
     **/
    @Override
    public String dologin(String username, String password) {
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        //执行登录
        user.login(usernamePasswordToken);
        return dologinSuccess( user, LivContextUtils.getResponse()) ;
    }

    @Override
    public String dologin(UsernamePasswordToken token,HttpServletResponse response) {
        Subject user = SecurityUtils.getSubject();
        //执行登录
        user.login(token);
        return dologinSuccess( user,response) ;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.27 16:02
     * @Description: 登录成功的操作
     **/
    private String dologinSuccess(Subject user,HttpServletResponse response) {
        //清除缓存
        clearCache();
        //生成token
        String jwttoken = JwtUtil.sign((String)user.getPrincipals().getPrimaryPrincipal());
        //返回token到reponse
        JwtUtil.tokenStore(response,jwttoken,false);
        //缓存用户登录信息
        PrincipalCollection principalCollection = user.getPrincipals();
        CacheFactory.getLoginSuccessSubjectCache().put(jwttoken,principalCollection);
        return jwttoken;
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.26 17:14
     * @Description: 清空用户相关缓存
     **/
    public void clearCache(){
        //清空认证缓存
        Subject subject = SecurityUtils.getSubject();
        CacheFactory.getCache(CacheFactory.SHIRO_AUTHORIZATIONCACHENAME).remove(subject.getPrincipal());
        CacheFactory.getCache(CacheFactory.SHIRO_AUTHENTICATIONCACHENAME).remove(subject.getPrincipal());

        //清空 角色权限缓存
        Cache roleCache = CacheFactory.getCache(CacheFactory.ROLE_PERMISSION_CACHE);
        if(subject.isAuthenticated()){
            User user = findByUserName(subject.getPrincipal().toString());
            List<Role> roles = roleService.getUserRoles(user.getUserId());
            for (int i = 0; i < roles.size(); i++) {
                roleCache.remove(roles.get(i).getRoleName());
            }
            roleCache.remove(ShiroRoles.SUPERMAN);
            roleCache.remove(ShiroRoles.SYSMAN);
            roleCache.remove(ShiroRoles.GUEST);
            roleCache.remove(ShiroRoles.USER);
            roleCache.remove(ShiroRoles.ANONYMOUS);
            roleCache.remove(subject.getPrincipal());
        }
    }
    /**
     * @Author: LiV
     * @Date: 2020.4.27 16:02
     * @Description: 刷新缓存的操作//每次请求需要更新缓存//
     * 参考：StatelessAccessControlFilter.java
     **/
    public void reDologinSuccess(HttpServletResponse response,String token) {
        boolean renewalToken = "true".equals(AppConst.USER_LOGIN_TOKEN_RENEWAL_ONACCESS);
        //返回token到reponse
        String newtoken = JwtUtil.tokenStore(response,token,renewalToken);
        if(renewalToken){
            //缓存刷新用户登录信息
            CacheFactory.getLoginSuccessSubjectCache().put(newtoken,CacheFactory.getLoginSuccessSubjectCache().get(token));
            CacheFactory.getLoginSuccessSubjectCache().remove(token);
        }
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.24 17:05
     * @Description: 退出系统
     **/
    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();

        //清除缓存
        clearCache();
        //退出的话还是用 response中的token，request中的有可能已经被更新，与缓存中不一致
        CacheFactory.getLoginSuccessSubjectCache().remove(getResponseToken());

        //这特么有毛用
        subject.logout();
    }

    @Override
    public UserDO getCurUser() {
        Subject user = SecurityUtils.getSubject();
        //这里判断用户是否已登录// 判断是否有ANONYMOUS角色，判断角色的动作可以初始化缓存，使得后续可以获取到用户信息
        if(user.isAuthenticated()&&user.hasRole(ShiroRoles.ANONYMOUS)){
            return (UserDO)CacheFactory.getCache(CacheFactory.SHIRO_AUTHORIZATIONCACHENAME).get(user.getPrincipal());
        }
        return null;
    }

    /**
     * 获取请求中的token,首先从请求头中获取,如果没有,则尝试从请求参数中获取
     *
     * @return
     */
    private String getResponseToken() {
        HttpServletResponse httpRep = LivContextUtils.getResponse();
        String token = httpRep.getHeader(LivContextUtils.REQUEST_AUTH_HEADER);
        return token;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.19 17:05
     * @Description: 用户注册
     **/
    public boolean reg(User user){
        PasswordHelper.encryptNewUserPassword(user);
        return mapper.insert(user)>0;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.19 20:44
     * @Description: 根据用户名查询用户
     **/
    @Override
    public User findByUserName(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name",username);
        List<User> user = mapper.selectList(qw);
        if(user != null&&user.size()>0) {
            return user.get(0);
        }
        return null;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.20 14:22
     * @Description: 解锁用户
     **/
    @Override
    public void unlock(String username) {
        RetryLimitHashedCredentialsMatcher matcher = (RetryLimitHashedCredentialsMatcher)LivContextUtils.getBean("credentialsMatcher");
        matcher.unlockAccount(username);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.20 14:22
     * @Description: 解锁用户
     **/
    @Override
    public void lock(String username) {
        RetryLimitHashedCredentialsMatcher matcher = (RetryLimitHashedCredentialsMatcher)LivContextUtils.getBean("credentialsMatcher");
        matcher.lockAccount(username);
    }




}
