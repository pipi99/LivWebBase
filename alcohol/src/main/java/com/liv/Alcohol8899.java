package com.liv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author LiV
 * @Title:
 * @Package PACKAGE_NAME
 * @Description: 启动类
 * @date 2020.3.18  10:47
 * @email 453826286@qq.com
 */
@SpringBootApplication
public class Alcohol8899 extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Alcohol8899.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Alcohol8899.class);
    }
}
