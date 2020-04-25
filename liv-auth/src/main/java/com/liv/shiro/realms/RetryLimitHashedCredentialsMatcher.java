package com.liv.shiro.realms;

import com.liv.dao.datamodel.User;
import com.liv.service.UserService;
import com.liv.utils.AppConst;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description:
 * @date 2020.4.19  20:24
 * @email 453826286@qq.com
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager){
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Autowired
    private UserService userService;

    /**
     * @Author: LiV 
     * @Date: 2020.4.20 16:52
     * @Description: doCredentialsMatch 将传入的密码（token中），和数据库密码 （info中）进行密码比对验证
     **/
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userName = token.getPrincipal().toString();
        AtomicInteger retryCount = passwordRetryCache.get(userName);
        if (null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(userName,retryCount);
        }

        if (retryCount.incrementAndGet() > AppConst.LOG_RETRY_TIMES) {
            //如果用户登陆失败次数大于5次 抛出锁定用户异常 并修改数据库字段
            User user = userService.findByUserName(userName);
            if (user != null && !"1".equals(user.getLocked())){
                //修改数据库的状态字段为锁定
                user.setLocked("1");
                userService.updateById(user);
                throw new LockedAccountException("账号已被锁定（10分钟）,请联系管理员，或者稍后再试!"); //帐号锁定
            }
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if (matches){
            passwordRetryCache.remove(userName);
        }

        return matches;
    }

    /**
     * 根据用户名 解锁用户
     * @param username
     * @return
     */
    public void unlockAccount(String username){
        User user = userService.findByUserName(username);
        if(user == null) {
            throw new UnknownAccountException("没找到帐号:"+username);//没找到帐号
        }
        //修改数据库的状态字段为解锁
        user.setLocked("0");
        userService.updateById(user);
        passwordRetryCache.remove(username);
    }
}
