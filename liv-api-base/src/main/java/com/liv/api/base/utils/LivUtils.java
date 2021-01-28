package com.liv.api.base.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.utils
 * @Description: 工具类入口
 * @date 2020.6.12  11:34
 * @email 453826286@qq.com
 */
@Component
public class LivUtils {

    @Autowired
    public static CacheUtils cacheUtils;

    @Autowired
    public static EhcacheUtils ehcacheUtils;

    @Autowired
    public static LivContextUtils livContextUtils;

    @Autowired
    public static LivDateUtils livDateUtils = new LivDateUtils();

    @Autowired
    public static RedisUtils redisUtils;

    @Autowired
    public static LivPropertiesUtils livPropertiesUtils;

    public static IPUtils ipUtils = new IPUtils();

    public static LivCollectionUtils livCollectionUtils = new LivCollectionUtils();
}
