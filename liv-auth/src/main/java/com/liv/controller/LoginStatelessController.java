package com.liv.controller;

import com.liv.dao.UserMapper;
import com.liv.dao.datamodel.User;
import com.liv.service.UserService;
import com.liv.shiro.stateless.jwt.JwtUtil;
import com.liv.utils.AppConst;
import com.liv.web.base.BaseController;
import com.liv.web.base.ResultBody;
import com.liv.web.exception.LivExceptionStatus;
import com.liv.web.utils.LivContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@Api(tags = "无状态用户登录管理")
public class LoginStatelessController extends BaseController<UserMapper, User, UserService> {
    /**
     * post表单提交，登录
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录", notes="用户登录")
    @PostMapping("/dologin")
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
            int retryrtimes = getRetryTimes(username);
            String retrymsg = retryrtimes>0?retryrtimes+"次后将被锁定！":"账号已锁定！（10分钟后重试）";
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,"密码错误！"+ retrymsg);
        } catch (Throwable e) {
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,"未知错误！"+e.getMessage());
        }
        return rb;
    }

    private int getRetryTimes(String username){
        EhCacheManager cacheManager = LivContextUtils.getBean(EhCacheManager.class);
        Cache<String, AtomicInteger> passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        AtomicInteger retryCount = passwordRetryCache.get(username);
        int retryrtimes = AppConst.LOG_RETRY_TIMES-retryCount.intValue();
        return retryrtimes;
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation(value = "用户退出", notes="用户退出")
    @GetMapping("/logout")
    public ResultBody logout() {
        SecurityUtils.getSubject().logout();
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
