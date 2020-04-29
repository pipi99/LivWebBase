package com.liv.shiro.stateless.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
public class JwtProperties {
    //token过期时间，单位分钟
    Integer tokenExpireTime;
    //token加密密钥
    String secretKey;
}
