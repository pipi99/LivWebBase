package com.liv.api.base.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.interceptor
 * @Description:
 * @date 2020.12.29  11:41
 * @email 453826286@qq.com
 */
@Data
@Component
@ConfigurationProperties(prefix="mybatis-plus.db-config")
public class SchemaProp {
    private String schema = ""; //数据模式
}
