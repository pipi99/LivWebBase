package com.liv.api.auth.shiro.stateless.filters;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.liv.api.auth.shiro.stateless.jwt.JwtUtil;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.base.base.ResultBody;
import com.liv.api.base.exception.LivExceptionStatus;
import com.liv.api.base.utils.LivContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.stateless
 * @Description: 每次请求都验证token ,判断是否登录 , 支持 api 登录
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
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//        log.debug("isAccessAllowed");
        return false;//跳到onAccessDenied处理
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 10:42
     * @Description: 被上一个方法拒绝后调用，验证token
     **/
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = LivContextUtils.getRequestToken(WebUtils.toHttp(request));
        boolean tokenIsAvaliable = JwtUtil.verify(token);
        //有效token
        if(tokenIsAvaliable){
            /**又是登录请求，已登录系统，无需再登录*/
            if(isLoginRequest(request, response)){
                remindHasLogin(response);
                return false;
            }else {
                //token设置到 response /cookie ,【根据条件判断是否设置为重新生成】
                LivContextUtils.getBean("apiUserService",UserService.class).refreshToken(WebUtils.toHttp(response),token);
                return true;
            }
        }else if (isLoginRequest(request, response)) {
            /**用来判断当前请求的方法是不是POST，*/
            if (WebUtils.toHttp(request).getMethod().equals(HttpMethod.POST.name())) {
                return true;
            }else {
                remindPOST(response); //提醒用户使用POST登录系统
                return false;
            }
        }else{
            remindLogin(response); //提醒用户登录系统
            //返回false终止filter链
            return false ;
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
     * @Author: LiV
     * @Date: 2020.4.22 10:43
     * @Description: 用户未登录，返回403
     **/
    private void remindPOST(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(JSON.toJSONString(ResultBody.error(LivExceptionStatus.FORBIDDEN,"请使用POST请求登录系统！")));
    }

}
