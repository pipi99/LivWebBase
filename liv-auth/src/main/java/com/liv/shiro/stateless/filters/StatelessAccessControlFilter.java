package com.liv.shiro.stateless.filters;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.liv.service.UserService;
import com.liv.shiro.stateless.jwt.JwtUtil;
import com.liv.utils.AppConst;
import com.liv.web.base.ResultBody;
import com.liv.web.exception.LivExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.stateless
 * @Description: 每次请求都验证token ,判断是否登录
 * @date 2020.4.21  16:04
 * @email 453826286@qq.com
 */

@Slf4j
public class StatelessAccessControlFilter extends AccessControlFilter {

    /**
     * @Author: LiV
     * @Date: 2020.4.22 08:46
     * @Description: 是否允许访问，默认不允许，交由  addcessDenied 判断token
     **/
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
//        //检查url是否设置了角色参数
//        String[] roles = (String[])mappedValue;
//        if(roles == null) {
//            return true;//如果没有设置角色参数，默认成功
//        }
//        for(String role : roles) {
//            //当前用户是否有角色
//            if(getSubject(request, response).hasRole(role)) {
//                return true;
//            }
//        }
        return false;//跳到onAccessDenied处理
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 10:42
     * @Description: 被上一个方法拒绝后调用，验证token
     **/
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = getRequestToken(request);
        boolean tokenIsAvaliable = JwtUtil.verify(token);
        /**判断当前请求是不是 登录请求地址，并且是未登录状态*/
         if (isLoginRequest(request, response)&&(ObjectUtils.isEmpty(token)||!tokenIsAvaliable)) {
            /**用来判断当前请求的方法是不是POST，*/
            if (WebUtils.toHttp(request).getMethod().equals(HttpMethod.POST.name())) {
                return true;
            }else {
                return false;
            }
        }else {
            //失效或者未登录
             /**正常访问的链接，判断token是否有效*/
            if (ObjectUtils.isEmpty(token)||!tokenIsAvaliable) {
                remindLogin(response); //提醒用户登录系统
                //返回false终止filter链
                return false ;

            }else{
                /**又是登录请求，已登录系统，无需再登录*/
                if(isLoginRequest(request, response)){
                    remindHasLogin(response);
                    return false;
                }else {
                    //token续签
//                    JwtUtil.tokenStore(WebUtils.toHttp(response),token,true);
                    return true;
                }
            }
        }
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 10:43
     * @Description: 用户未登录，返回401
     **/
    private void remindHasLogin(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(JSON.toJSONString(ResultBody.success("您已登录系统，无需重复登录！")));
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 10:43
     * @Description: 用户未登录，返回401
     **/
    private void remindLogin(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(JSON.toJSONString(ResultBody.error(LivExceptionStatus.UNAUTHORIZED,"请登录系统！")));
    }

    /**
     * 获取请求中的token,首先从请求头中获取,如果没有,则尝试从请求参数中获取
     *
     * @param request
     * @return
     */
    private String getRequestToken(ServletRequest request) {
        HttpServletRequest httpReq = WebUtils.toHttp(request);

        String token = AppConst.getCookieJwtToken(httpReq);

        if (StringUtils.isBlank(token)) {
            token = httpReq.getHeader(AppConst.REQUEST_AUTH_HEADER);
        }

        if (StringUtils.isBlank(token)) {
            token = httpReq.getParameter(AppConst.REQUEST_AUTH_HEADER);
        }
        return token;
    }
}
