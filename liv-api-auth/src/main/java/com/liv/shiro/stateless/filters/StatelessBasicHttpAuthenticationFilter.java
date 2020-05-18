package com.liv.shiro.stateless.filters;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.liv.service.UserService;
import com.liv.shiro.stateless.jwt.JwtUtil;
import com.liv.utils.AppConst;
import com.liv.web.api.base.base.ResultBody;
import com.liv.web.api.base.exception.LivExceptionStatus;
import com.liv.web.api.base.utils.LivContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.stateless
 * @Description: 每次请求都验证token ,判断是否登录
 * @date 2020.4.21  16:04
 * @email 453826286@qq.com
 */

@Slf4j
public class StatelessBasicHttpAuthenticationFilter extends BasicHttpAuthenticationFilter {

    /**
     * @Author: LiV
     * @Date: 2020.4.22 08:46
     * @Description: 是否允许访问
     **/
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
        String token = getRequestToken(request);
        boolean tokenIsAvaliable = JwtUtil.verify(token);
        /**正常访问的链接，判断token是否有效*/
        if (ObjectUtils.isEmpty(token)||!tokenIsAvaliable) {
            return false;
        }else{
            //token续签
            LivContextUtils.getBean("userService", UserService.class).reDologinSuccess(WebUtils.toHttp(response),token);
            return true;
        }
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        UsernamePasswordToken token = (UsernamePasswordToken)this.createToken(request,response);
        if(StringUtils.isEmpty(token.getUsername())){
            return false;
        }
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try{
            LivContextUtils.getBean("userService",UserService.class).dologin(token,WebUtils.toHttp(response));
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
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
