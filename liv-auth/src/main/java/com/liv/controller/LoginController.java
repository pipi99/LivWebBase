package com.liv.controller;

import com.liv.dao.UserMapper;
import com.liv.dao.datamodel.User;
import com.liv.service.UserService;
import com.liv.shiro.cache.CacheFactory;
import com.liv.utils.AppConst;
import com.liv.web.api.base.BaseController;
import com.liv.web.api.base.ResultBody;
import com.liv.web.api.exception.LivExceptionStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.Cache;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.controller
 * @Description: 用户登录管理
 * @date 2020.4.19  17:32
 * @email 453826286@qq.com
 */
@RestController
@Api(tags = "用户登录管理")
public class LoginController extends BaseController<UserMapper, User, UserService> {
    /**
     * post表单提交，登录
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录", notes="用户登录")
    @PostMapping("/login")
    public ResultBody login(String username, String password) {
        ResultBody rb = null;
        try {
            String jwttoken = service.dologin(username,password);
            rb = ResultBody.success(jwttoken);
            return rb;
        } catch (UnknownAccountException e) {
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,e.getMessage());
        } catch (DisabledAccountException e) {
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,e.getMessage());
        } catch (IncorrectCredentialsException e) {

            //密码校验失败时执行这个exception，锁定后执行 mather中的exception
            int retryrtimes = getRetryTimes(username);
            String retrymsg = retryrtimes+"次后将被锁定！";
            /**次数用完，直接锁定账号*/
            if(retryrtimes<=0){
                retrymsg = "账号已被锁定（"+ AppConst.USER_LOGIN_FAIL_LOCKED_TIME +"分钟）,请联系管理员，或者稍后再试!";
                service.lock(username);
            }
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,"密码错误！"+ retrymsg);
        } catch (Throwable e) {
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,"未知错误！"+e.getMessage());
        }
        return rb;
    }

    private int getRetryTimes(String username){
        Cache<String, AtomicInteger> passwordRetryCache =  CacheFactory.getPasswordRetryCache();
        AtomicInteger retryCount = passwordRetryCache.get(username);
        int retryrtimes = AppConst.LOG_FAIL_RETRY_TIMES-retryCount.intValue();
        return retryrtimes;
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation(value = "用户退出", notes="用户退出")
    @GetMapping("/logout")
    public ResultBody logout() {
        service.logout();
        return ResultBody.success("退出成功");
    }

    /**
     * 解除admin 用户的限制登录
     * 写死的 方便测试
     * @return
     */
    @GetMapping("/unlock/{username}")
    @ApiOperation(value = "解锁用户", notes="解锁用户")
    public ResultBody unlockAccount(@PathVariable("username") String username){
        service.unlock(username);
        return ResultBody.success("用户解锁成功:"+username);
    }
}
