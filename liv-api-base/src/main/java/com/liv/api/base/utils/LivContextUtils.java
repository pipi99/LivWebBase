package com.liv.api.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.utils
 * @Description: 工程上下文工具类
 * @date 2020.4.19  16:11
 * @email 453826286@qq.com
 */
@Component
public class LivContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 获取request对象
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取Response对象
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取Session对象
     */
    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(LivContextUtils.applicationContext == null) {
            LivContextUtils.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    //request请求头属性
    public static String REQUEST_AUTH_HEADER="x-auth-token";
    /**
     * 获取请求中的token,首先从请求头中获取,如果没有,则尝试从请求参数中获取
     *
     * @param httpReq
     * @return
     */
    public static String getRequestToken(HttpServletRequest httpReq) {

        String token = getCookieJwtToken(httpReq);

        if (StringUtils.isBlank(token)) {
            token = httpReq.getHeader(REQUEST_AUTH_HEADER);
        }

        if (StringUtils.isBlank(token)) {
            token = httpReq.getParameter(REQUEST_AUTH_HEADER);
        }
        return token;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 16:05
     * @Description: 获取cookie的jwt
     **/
    private static String getCookieJwtToken(HttpServletRequest req){
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
