package com.liv.utils;

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
public class AppConst {
    /**用户登录失败重试次数*/
    public static final Integer LOG_RETRY_TIMES = 5;

    //request请求头属性
    public static final String REQUEST_AUTH_HEADER="x-auth-token";

    //JWT-account
    public static final String ACCOUNT = "account";
    //JWT-currentTimeMillis
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    //Shiro redis 前缀
    public static final String PREFIX_SHIRO_CACHE = "storyweb-bp:cache:";
    //redis-key-前缀-shiro:refresh_token
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "storyweb-bp:refresh_token:";

    public final static String SHIRO_AUTHENTICATIONCACHENAME = "shiro-AuthenticationCacheName";
    public final static String SHIRO_AUTHORIZATIONCACHENAME = "shiro-AuthorizationCacheName";

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
