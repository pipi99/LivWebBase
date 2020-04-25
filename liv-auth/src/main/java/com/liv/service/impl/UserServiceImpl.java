package com.liv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.UserMapper;
import com.liv.dao.datamodel.User;
import com.liv.service.UserService;
import com.liv.shiro.realms.RetryLimitHashedCredentialsMatcher;
import com.liv.shiro.stateless.jwt.JwtUtil;
import com.liv.utils.PasswordHelper;
import com.liv.web.base.BaseService;
import com.liv.web.utils.LivContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service
public class UserServiceImpl extends BaseService<UserMapper, User> implements UserService {

    @Autowired
    private EhCacheManager cacheManager;
    private Cache<String,PrincipalCollection> subjectCache;
    @PostConstruct
    public void cacheInit(){
        subjectCache = cacheManager.getCache("subjectCache");
    }
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
        String jwttoken = JwtUtil.sign((String)user.getPrincipals().getPrimaryPrincipal());

        //返回token
        JwtUtil.tokenStore(LivContextUtils.getResponse(),jwttoken,false);

        //缓存用户登录信息
        PrincipalCollection principalCollection = user.getPrincipals();
        subjectCache.put(jwttoken,principalCollection);

        return jwttoken;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.24 17:05
     * @Description: 退出系统
     **/
    @Override
    public String logout() {

        return null;
    }


    /**
     * @Author: LiV
     * @Date: 2020.4.19 17:05
     * @Description: 用户注册
     **/
    public boolean reg(User user){
        PasswordHelper.encryptNewUserPassword(user);
        return this.mapper().insert(user)>0;
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
        List<User> user = mapper().selectList(qw);
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




}
