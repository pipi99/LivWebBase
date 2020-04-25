package com.liv.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description: shiro拦截机制配置
 * @date 2020.4.20  15:42
 * @email 453826286@qq.com
 */
public class ShiroUrlFilter {
    public static ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login222");
        bean.setUnauthorizedUrl("/unauthor");

        Map<String, Filter> filters = new LinkedHashMap<>();
//      filters.put("perms", urlPermissionsFilter());
        filters.put("anon", new AnonymousFilter());
        bean.setFilters(filters);

        //basicfilter


        //shiro配置过滤规则少量的话可以用hashMap,数量多了要用LinkedHashMap,保证有序，原因未知
        Map<String, String> chains = new LinkedHashMap<>();
        chains.put("/dologin", "anon");
        chains.put("/login", "anon");
        //swagger
        chains.put("/swagger-ui.html", "anon");
        chains.put("/swagger-ui.html/**", "anon");
        chains.put("/swagger-resources/**", "anon");
        chains.put("/swagger/**", "anon");
        chains.put("/v2/**", "anon");
        chains.put("/webjars/**", "anon");

        chains.put("/**", "authc");
        bean.setFilterChainDefinitionMap(chains);
        return bean;
    }
}
