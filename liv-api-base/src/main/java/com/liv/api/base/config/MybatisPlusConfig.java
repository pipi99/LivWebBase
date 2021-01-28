package com.liv.api.base.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.liv.api.base.interceptor.MyBatisSqlInterceptor;
import com.liv.api.base.interceptor.SchemaProp;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
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
@EnableConfigurationProperties({ SchemaProp.class})
public class MybatisPlusConfig {
    @Resource(type = com.liv.api.base.interceptor.SchemaProp.class)
    SchemaProp schemaProp;

//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor page = new PaginationInterceptor();
//        return page;
//    }
    /**
     *  新版本后再使用，当前版本不支持
     *  新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    @Bean
    public String myInterceptor(SqlSessionFactory sqlSessionFactory) {
        //实例化插件
        MyBatisSqlInterceptor sqlInterceptor = new MyBatisSqlInterceptor();
        //创建属性值
        Properties properties = new Properties();
        properties.setProperty("schema",schemaProp.getSchema());
        //将属性值设置到插件中
        sqlInterceptor.setProperties(properties);
        //将插件添加到SqlSessionFactory工厂
        sqlSessionFactory.getConfiguration().addInterceptor(sqlInterceptor);
        return "interceptor";
    }
}
