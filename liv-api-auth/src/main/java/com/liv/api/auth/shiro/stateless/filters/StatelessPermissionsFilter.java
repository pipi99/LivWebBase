package com.liv.api.auth.shiro.stateless.filters;

import com.alibaba.fastjson.JSON;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.shiro.stateless.jwt.JwtUtil;
import com.liv.api.auth.utils.ApiAuthUtils;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.base.base.ResultBody;
import com.liv.api.base.exception.LivExceptionStatus;
import com.liv.api.base.utils.LivContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.stateless
 * @Description: 权限filter ，验证权限
 *
 * 1、根据当前访问地址与用户维护的菜单按钮地址进行匹配是否有访问权限
 * 2、当访问地址未维护为菜单和按钮配置地址的，则登录即可访问。不做角色权限判断。
 *
 * @date 2020.4.21  16:04
 * @email 453826286@qq.com
 */

@Slf4j
public class StatelessPermissionsFilter extends PermissionsAuthorizationFilter {

    /**
     * @Author: LiV
     * @Date: 2020.4.22 08:46
     * @Description: 是否允许访问
     **/
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = SecurityUtils.getSubject();
        String urlPermissionFlag = WebUtils.toHttp(request).getServletPath();
        urlPermissionFlag = ApiAuthUtils.getInstance(WebUtils.toHttp(request)).getPermissionStrByUrl(urlPermissionFlag);

        //默认0位上是查看权限
        if(!StringUtils.isEmpty(urlPermissionFlag)){
            //菜单权限过滤  //根据url判断是否有权限
            try{
                subject.checkPermission("+"+urlPermissionFlag+"+1");
            }catch (Exception e){
                log.error(e.getMessage(),e);
//                remindPermission( response, WebUtils.toHttp(request).getRequestURI());
                return false;
            }
        }
        return true;
    }


    /**
     * @Author: LiV
     * @Date: 2020.4.22 10:43
     * @Description: 用户无权限，返回401
     **/
    private void remindPermission(ServletResponse response,String url) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("text/html;charset=utf-8");
        httpResponse.getWriter().write(JSON.toJSONString(ResultBody.error(LivExceptionStatus.UNAUTHORIZED,"你没有访问"+url+"的权限！")));
    }
}
