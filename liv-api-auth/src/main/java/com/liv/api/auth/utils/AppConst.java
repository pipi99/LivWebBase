package com.liv.api.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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

    //request请求头属性
    public static String REQUEST_AUTH_HEADER="x-auth-token";
    //JWT-account
    public static String ACCOUNT = "account";
    //JWT-currentTimeMillis
    public static String CURRENT_TIME_MILLIS = "currentTimeMillis";


    @PostConstruct
    private void constInit(){
        LOG_FAIL_RETRY_TIMES = livPropertiesUtils.getMapProps().get("user-login-fail-retry-times")==null?LOG_FAIL_RETRY_TIMES:Integer.parseInt(livPropertiesUtils.getMapProps().get("user-login-fail-retry-times"));
        USER_LOGIN_TIMEOUTS = livPropertiesUtils.getMapProps().get("user-login-timeouts")==null?USER_LOGIN_TIMEOUTS:Integer.parseInt(livPropertiesUtils.getMapProps().get("user-login-timeouts"));
        USER_LOGIN_TOKEN_RENEWAL_ONACCESS = livPropertiesUtils.getMapProps().get("user-login-token-renewal-onaccess")==null?USER_LOGIN_TOKEN_RENEWAL_ONACCESS:livPropertiesUtils.getMapProps().get("user-login-token-renewal-onaccess");
        USER_LOGIN_FAIL_LOCKED_TIME = livPropertiesUtils.getMapProps().get("user-login-fail-locked-time")==null?USER_LOGIN_FAIL_LOCKED_TIME:Integer.parseInt(livPropertiesUtils.getMapProps().get("user-login-fail-locked-time"));
        USE_CACHE = livPropertiesUtils.getMapProps().get("usecache")==null?USE_CACHE:livPropertiesUtils.getMapProps().get("usecache");
    }
    /**
     * @Author: LiV
     * @Date: 2020.4.22 16:05
     * @Description: 获取cookie的jwt
     **/
    public static String getCookieJwtToken(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();//根据Cookie的名字获取对应的请求头的值
                if(REQUEST_AUTH_HEADER.equals(cookieName)){
                    return cookieValue;
                }
            }
        }
        return null;
    }
}
