package com.liv.api.auth.utils;

import com.liv.api.base.utils.LivPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.utils
 * @Description: 系统全局常量配置
 * @date 2020.4.20  17:01
 * @email 453826286@qq.com
 */
@Component
public class AppConst {
    @Autowired
    private LivPropertiesUtils livPropertiesUtils;

    /**用户登录失败重试次数*/
    public static Integer LOG_FAIL_RETRY_TIMES = 5;
    /**用户登录失败锁定时常*/
    public static Integer USER_LOGIN_FAIL_LOCKED_TIME = 10;
    /**用户登录会话时常(分钟)*/
    public static Integer USER_LOGIN_TIMEOUTS = 30;
    /**用户每次访问系统是否刷新token的值*/
    public static String USER_LOGIN_TOKEN_RENEWAL_ONACCESS = "false";
    /**用户使用的缓存类型*/
    public static String USE_CACHE = "redis";

    //JWT-account
    public static String ACCOUNT = "account";
    //JWT-currentTimeMillis
    public static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    //最大支持的权限位数 。10个权限
    public static int MAX_BIT_PERMISSION = 1023;


    //菜单状态
    public static String MENU_LOGIN  = "login";// 登录访问
    public static String MENU_PERM  = "perm";// 授权访问
    public static String MENU_OPEN  = "open";// 任意访问


    @PostConstruct
    private void constInit(){
        LOG_FAIL_RETRY_TIMES = livPropertiesUtils.getMapProps().get("user-login-fail-retry-times")==null?LOG_FAIL_RETRY_TIMES:Integer.parseInt(livPropertiesUtils.getMapProps().get("user-login-fail-retry-times"));
        USER_LOGIN_TIMEOUTS = livPropertiesUtils.getMapProps().get("user-login-timeouts")==null?USER_LOGIN_TIMEOUTS:Integer.parseInt(livPropertiesUtils.getMapProps().get("user-login-timeouts"));
        USER_LOGIN_TOKEN_RENEWAL_ONACCESS = livPropertiesUtils.getMapProps().get("user-login-token-renewal-onaccess")==null?USER_LOGIN_TOKEN_RENEWAL_ONACCESS:livPropertiesUtils.getMapProps().get("user-login-token-renewal-onaccess");
        USER_LOGIN_FAIL_LOCKED_TIME = livPropertiesUtils.getMapProps().get("user-login-fail-locked-time")==null?USER_LOGIN_FAIL_LOCKED_TIME:Integer.parseInt(livPropertiesUtils.getMapProps().get("user-login-fail-locked-time"));
        USE_CACHE = livPropertiesUtils.getMapProps().get("usecache")==null?USE_CACHE:livPropertiesUtils.getMapProps().get("usecache");
    }

}
