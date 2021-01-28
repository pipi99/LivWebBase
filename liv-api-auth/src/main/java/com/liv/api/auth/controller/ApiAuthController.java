package com.liv.api.auth.controller;

import com.liv.api.auth.dao.MenuMapper;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.service.MenuService;
import com.liv.api.auth.service.RoleService;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.shiro.cache.CacheFactory;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.auth.utils.PasswordHelper;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.ResultBody;
import com.liv.api.base.exception.LivExceptionStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 菜单管理控制器
 **/
@RestController("api_auth_Controller")
@CrossOrigin
@RequestMapping(value = "/sp_auth")
@Api(tags = "AUTH API组件控制器")
@Slf4j
public class ApiAuthController  {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "查询当前用户菜单列表", notes="查询当前用户菜单列表")
    @GetMapping(value="/menu/list")
    public ResultBody menulist() throws Exception {
        return ResultBody.success(menuService.getCurUserMenus());
    }

    @ApiOperation(value = "用户注册", notes="注册用户")
    @GetMapping(value="/reg")
    public ResultBody reg(String username,String alias,String password) throws Exception {
        User user = new User();
        user.setUserName(username);
        user.setAlias(alias);
        user.setPassword(password);
        return ResultBody.success(userService.reg(user));
    }
    /**
     * post表单提交，登录
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "用户登录", notes="用户登录")
    @PostMapping("/login")
    public ResultBody login(@RequestParam String username, @RequestParam String password) {
        ResultBody rb = null;
        try {
            String jwttoken = userService.dologin(username,password);
            //获取登录用户信息
            UserVO user = userService.getCurUser().getUser();
            Map map = new HashMap();
            map.put("token",jwttoken);
            map.put("user",user);
            rb = ResultBody.success(map);
            return rb;
        } catch (UnknownAccountException e) {
            log.error(e.getMessage(),e);
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,e.getMessage());
        } catch (DisabledAccountException e) {
            log.error(e.getMessage(),e);
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,e.getMessage());
        } catch (IncorrectCredentialsException e) {
            log.error(e.getMessage(),e);
            //密码校验失败时执行这个exception，锁定后执行 mather中的exception
            int retryrtimes = getRetryTimes(username);
            String retrymsg = retryrtimes+"次后将被锁定！";
            /**次数用完，直接锁定账号*/
            if(retryrtimes<=0){
                retrymsg = "账号已被锁定（"+ AppConst.USER_LOGIN_FAIL_LOCKED_TIME +"分钟）,请联系管理员，或者稍后再试!";
                userService.lock(username);
            }
            rb = ResultBody.error(LivExceptionStatus.UNAUTHORIZED,"密码错误！"+ retrymsg);
        } catch (Throwable e) {
            log.error(e.getMessage(),e);
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
    @ApiOperation(value = "获取当前登录用户信息", notes="获取当前登录用户信息")
    @GetMapping("/getCurUser")
    public ResultBody getCurUser() {
        return ResultBody.success(userService.getCurUser());
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation(value = "用户退出", notes="用户退出")
    @GetMapping("/logout")
    public ResultBody logout() {
        userService.logout();
        return ResultBody.success("退出成功");
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.13 14:55
     * @Description: 用户的限制登录
     **/
    @GetMapping("/unlock/{username}")
    @ApiOperation(value = "解锁用户", notes="解锁用户")
    public ResultBody unlockAccount(@PathVariable("username") String username){
        userService.unlock(username);
        return ResultBody.success("用户解锁成功:"+username);
    }

    @ApiOperation(value = "根据ID查询用户", notes="根据给定的用户ID 查询用户")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/user/getById/{userId}")
    public ResultBody getById(@PathVariable("userId") Long userId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(userService.getById(userId));
    }

    @ApiOperation(value = "查询用户列表", notes="查询用户列表")
    @GetMapping(value="/user/list")
    public ResultBody userlist() throws Exception {
        return ResultBody.success(userService.list());
    }

    @ApiOperation(value = "获取当前登录用户", notes="获取当前登录用户")
    @GetMapping(value="/user/getCurrUser")
    public ResultBody getCurrUser() {
        return ResultBody.success(userService.getCurUser().getUser());
    }
}
