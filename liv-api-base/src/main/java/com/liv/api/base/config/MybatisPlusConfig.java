package com.liv.api.base.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.config
 * @Description: MybatisPlusConfig
 * @date 2020.4.17  19:36
 * @email 453826286@qq.com
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor()
    {
        PaginationInterceptor page = new PaginationInterceptor();
        return page;
    }

    /**
     * 打印 sql  ,logback
     */

}
