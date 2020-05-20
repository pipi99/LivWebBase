package com.liv.web.api.shiro.stateless.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.stateless.jwt
 * @Description:
 * @date 2020.4.21  18:53
 * @email 453826286@qq.com
 */

@ConfigurationProperties(prefix = "token")
@Data
@Component
public class JwtProperties {
    //token过期时间，单位分钟
    Integer tokenExpireTime = 604800;
    //token加密密钥
    String secretKey = "mmnzdmwhahan";
}
