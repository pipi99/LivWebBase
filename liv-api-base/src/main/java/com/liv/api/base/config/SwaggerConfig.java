package com.liv.api.base.config;

import com.liv.api.base.utils.LivContextUtils;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.config
 * @Description: SwaggerUI配置 , 后台支撑主要为web开发 ，swagger的拦截这里 为 basic 拦截 ， 不需要输入 token
 * @date 2020.4.15  16:23
 * @email 453826286@qq.com
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
        @Bean
        public Docket createRestApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com"))
                    .paths(PathSelectors.any())
                    .build();
        }

    /**关于 登录的配置，右上角出现一个 authorize **/
//    @Bean
//    public Docket swaggerSpringMvcPlugin() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
//                //带注解的生成api
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
//    }
//    private List<ApiKey> securitySchemes() {
//        List<ApiKey> apiKeyList= new ArrayList<ApiKey>();
//        apiKeyList.add(new ApiKey(LivContextUtils.REQUEST_AUTH_HEADER, LivContextUtils.REQUEST_AUTH_HEADER, "header"));
//        return apiKeyList;
//    }
//
//    private List<SecurityContext> securityContexts() {
//        List<SecurityContext> securityContexts=new ArrayList<>();
//        securityContexts.add(
//                SecurityContext.builder()
//                        .securityReferences(defaultAuth())
//                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
//                        .build());
//        return securityContexts;
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        List<SecurityReference> securityReferences=new ArrayList<>();
//        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
//        return securityReferences;
//
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Swagger RESTful APIs")
                .description("webDesk Swagger API 服务")
                .termsOfServiceUrl("/swagger-ui.html")
                .contact(new Contact("liv", "", ""))
                .version("1.0")
                .build();
    }

}
