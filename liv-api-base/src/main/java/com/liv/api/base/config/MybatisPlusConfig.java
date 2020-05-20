package com.liv.api.base.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.config
 * @Description: MybatisPlusConfig
 * @date 2020.4.17  19:36
 * @email 453826286@qq.com
 */
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 打印 sql  ,logback
     */

}
