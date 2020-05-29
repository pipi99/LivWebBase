package com.liv.api.base.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.api.utils
 * @Description:
 * @date 2020.4.26  11:03
 * @email 453826286@qq.com
 */
@Data
@Component
@ConfigurationProperties(prefix="liv")
public class LivPropertiesUtils {
    private String simpleProp;
    private String[] arrayProps;
    private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值
    private List<String> listProp2 = new ArrayList<>(); //接收prop2里面的属性值
    private Map<String, String> mapProps = new HashMap<>(); //接收prop1里面的属性值
}
