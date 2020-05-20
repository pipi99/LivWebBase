package com.liv.web.api.shiro.realms;

import com.liv.web.api.dao.datamodel.User;
import com.liv.web.api.service.UserService;
import com.liv.web.api.shiro.cache.CacheFactory;
import com.liv.web.api.utils.AppConst;
import com.liv.web.api.web.api.base.utils.LivDateUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description: 用户密码校验
 * @date 2020.4.19  20:24
 * @email 453826286@qq.com
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

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

        /***账号已锁定*/
        User user = userService.findByUserName(userName);
        if(user != null&&"1".equals(user.getLocked())){
            /**添加锁定时间，当前时间与锁定时间做判断*/
            Date date = LivDateUtils.getCurDateAfterOrBefore(Calendar.MINUTE,AppConst.USER_LOGIN_FAIL_LOCKED_TIME*-1);
            if(date.after(user.getLocktime())){
                //解锁
                unlockAccount(userName);
            }else{
                throw new LockedAccountException("账号已被锁定（"+ AppConst.USER_LOGIN_FAIL_LOCKED_TIME +"分钟）,请联系管理员，或者稍后再试!"); //帐号锁定
            }
        }

        //密码错误，重试次数
        Cache<String, AtomicInteger> passwordRetryCache =  CacheFactory.getPasswordRetryCache();
        AtomicInteger retryCount = passwordRetryCache.get(userName);
        if (null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(userName,retryCount);
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if (matches){
            passwordRetryCache.remove(userName);
        }else{
            retryCount.incrementAndGet();
            passwordRetryCache.put(userName,retryCount);
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
        //密码错误，重试次数
        CacheFactory.getPasswordRetryCache().remove(username);
    }

    /**
     * 根据用户名 解锁用户
     * @param username
     * @return
     */
    public void lockAccount(String username){
        User user = userService.findByUserName(username);
        if(user == null) {
            throw new UnknownAccountException("没找到帐号:"+username);//没找到帐号
        }
        //修改数据库的状态字段为锁定
        user.setLocked("1");
        user.setLocktime(LivDateUtils.getCurDate());
        userService.updateById(user);
    }
}
