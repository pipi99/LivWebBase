package com.liv.shiro.stateless.filters;

import com.liv.utils.AppConst;
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
 * @Package com.liv.shiro.stateless
 * @Description: StatelessShiroUrlFilter
 * @date 2020.4.21  16:02
 * @email 453826286@qq.com
 */
public class StatelessAuthcFilterFactoryBean {
    /**
     * Shiro 对 Servlet 容器的 FilterChain 进行了代理，即 ShiroFilter 在继续 Servlet 容器的 Filter 链的执行之前，通过 ProxiedFilterChain 对 Servlet 容器的 FilterChain 进行了代理；
     * 即先走 Shiro 自己的 Filter 体系，然后才会委托给 Servlet 容器的 FilterChain 进行 Servlet 容器级别的 Filter 链执行；
     * Shiro 的 ProxiedFilterChain 执行流程：1、先执行 Shiro 自己的 Filter 链；2、再执行 Servlet 容器的 Filter 链（即原始的 Filter）。
     **/
    public static ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        bean.setLoginUrl("/login");

        Map<String, Filter> filters = new LinkedHashMap<>();

        //shiro配置过滤规则少量的话可以用hashMap,数量多了要用LinkedHashMap,保证有序
        Map<String, String> chains = new LinkedHashMap<>();

        /**无需登录直接访问*/
        filters.put("anon",  new AnonymousFilter());
        /**验证是否登录*/
        filters.put("StatelessAuthcFilter",  new StatelessAccessControlFilter());


        //路径的填写排除工程上下文
        //swagger
        chains.put("/swagger-ui.html", "anon");
        chains.put("/swagger-ui.html/**", "anon");
        chains.put("/swagger-resources/**", "anon");
        chains.put("/swagger/**", "anon");
        chains.put("/v2/**", "anon");
        chains.put("/v2/**", "anon");
        chains.put("/webjars/**", "anon");
        chains.put("/configuration/ui", "anon");
        chains.put("/configuration/security", "anon");

        chains.put("/**", "StatelessAuthcFilter");

        /**自动弹出登录框*/
        filters.put("basicAuth",  new StatelessBasicHttpAuthenticationFilter());
        chains.put("/**", "basicAuth");

        bean.setFilters(filters);
        bean.setFilterChainDefinitionMap(chains);
        return bean;
    }
}
