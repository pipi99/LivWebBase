package com.liv.api.auth.shiro.stateless.filters;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.shiro.stateless.jwt.JwtUtil;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.base.utils.LivContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.stateless
 * @Description: 每次请求都验证token ,判断是否登录 ，支持basic 登录
  @date 2020.4.21  16:04
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
        String token = LivContextUtils.getRequestToken(WebUtils.toHttp(request));
        boolean tokenIsAvaliable = JwtUtil.verify(token);
        /**正常访问的链接，判断token是否有效*/
        if (tokenIsAvaliable) {
            //token续签
//            LivContextUtils.getBean("apiUserService", UserService.class).reDologinSuccess(WebUtils.toHttp(response),token);
            return true;
        }
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        UsernamePasswordToken token = (UsernamePasswordToken)this.createToken(request,response);
        if(StringUtils.isEmpty(token.getUsername())){
            return false;
        }
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try{
            LivContextUtils.getBean("apiUserService",UserService.class).dologin(token,WebUtils.toHttp(response));
        }catch(Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
}
