package com.liv.shiro;

import com.liv.service.UserService;
import com.liv.shiro.stateless.StatelessDefaultSubjectFactory;
import com.liv.shiro.stateless.jwt.JwtProperties;
import com.liv.utils.PasswordHelper;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.filter.DelegatingFilterProxy;
import javax.servlet.DispatcherType;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description: shiro配置
 * @date 2020.4.19  20:17
 * @email 453826286@qq.com
 */
//使用stateless下的config
//@Configuration
public class ShiroConfig {
//
//    @Autowired
//    @Lazy
//    private UserService userService;
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
//        filterRegistration.setEnabled(true);
//        filterRegistration.addUrlPatterns("/*"); //过滤规则，即所有的请求
//        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
//        return filterRegistration;
//    }
//
//    /**
//     * 这个即是上边调用的shiroFilter过滤器，也就是shiro配置的过滤器
//     * @return
//     */
//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean shiroFilter(){
//        return ShiroUrlFilter.getShiroFilterFactoryBean(securityManager());
//    }
//
//    /**
//     * @see org.apache.shiro.mgt.SecurityManager
//     * @return
//     */
//    @Bean(name="securityManager")
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setRealm(userRealm());
//        manager.setCacheManager(ehCacheManager());
//        manager.setSessionManager(defaultWebSessionManager());
//        return manager;
//    }
//    /**
//     * @see DefaultWebSessionManager
//     * @return
//     */
//    @Bean(name="sessionManager")
//    public DefaultWebSessionManager defaultWebSessionManager() {
//        //用于 Web 环境的实现，可以替代 ServletContainerSessionManager，自己维护着会话，直接废弃了 Servlet 容器的会话管理
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setCacheManager(ehCacheManager());
//        sessionManager.setGlobalSessionTimeout(1800000);
//        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionIdCookie(getSessionIdCookie());
//        return sessionManager;
//    }
//    /**
//     * 给shiro的sessionId默认的JSSESSIONID名字改掉
//     * @return
//     */
//    @Bean(name="sessionIdCookie")
//    public SimpleCookie getSessionIdCookie(){
//        SimpleCookie simpleCookie = new SimpleCookie("webcookie");
//        /**
//         * HttpOnly标志的引入是为了防止设置了该标志的cookie被JavaScript读取，
//         * 但事实证明设置了这种cookie在某些浏览器中却能被JavaScript覆盖，
//         * 可被攻击者利用来发动session fixation攻击
//         */
//        simpleCookie.setHttpOnly(true);
//        /**
//         * 设置浏览器cookie过期时间，如果不设置默认为-1，表示关闭浏览器即过期
//         * cookie的单位为秒 比如60*60为1小时
//         */
//        simpleCookie.setMaxAge(-1);
//        return simpleCookie;
//    }
//
//    /**
//     * @see UserRealm--->AuthorizingRealm
//     * @return
//     */
//    @Bean
//    @DependsOn(value="lifecycleBeanPostProcessor")
//    public UserRealm userRealm() {
//        UserRealm userRealm = new UserRealm();
//        userRealm.setName("livDataBaseUserRealm");
//        userRealm.setCredentialsMatcher(credentialsMatcher());
//        userRealm.setCacheManager(ehCacheManager());
//        return userRealm;
//    }
//
//    @Bean(name="credentialsMatcher")
//    public CredentialsMatcher credentialsMatcher() {
//        //这里的规则需要与  passwordHelper中 新增用户的密码加密规则 一致
//        return PasswordHelper.getMatcher(ehCacheManager());
//    }
//
//    @Bean
//    public EhCacheManager ehCacheManager(){
//        EhCacheManager cacheManager = new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:shiro/ehcache.xml");
//        return cacheManager;
//    }
//
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
}