package com.liv.shiro;

import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description: EhcacheConfig
 * @date 2020.4.19  21:31
 * @email 453826286@qq.com
 */
@Configuration
public class EhcacheConfig {
    /**
     * 设置为共享模式
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
}
