package com.liv.api.base.utils;

import com.liv.api.base.cache.EhCacheFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.utils
 * @Description: redisUtil
 * @date 2020.4.25  15:50
 * @email 453826286@qq.com
 */
@Component
public class EhcacheUtils {

    private EhcacheUtils() {
    }

    private static EhcacheUtils ehcacheUtils;

    @Autowired
    private EhCacheFactory ehCacheFactory;

    @PostConstruct
    public void init() {
        ehcacheUtils = this;
        ehcacheUtils.ehCacheFactory = ehCacheFactory;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return true=删除成功；false=删除失败
     */
    public static boolean del(final String key) {
        Boolean ret = ehcacheUtils.ehCacheFactory.getCache(key).remove(key);
        return ret != null && ret;
    }

    /**
     * 存入普通对象
     *
     * @param key Redis键
     * @param value 值
     */
    public static void set(final String key, final Object value) {
        Element ele = new Element(key,value);
        ehcacheUtils.ehCacheFactory.getCache(key).put(ele);
    }

    // 存储普通对象操作

    /**
     * 存入普通对象
     *
     * @param key 键
     * @param value 值
     * @param expireSeconds 有效期，单位秒
     */
    public static void set(final String key, final Object value, final long expireSeconds) {
        Cache cache = ehcacheUtils.ehCacheFactory.getCache(key,expireSeconds);
        Element ele = new Element(key,value);
        cache.put(ele);
    }

    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public static Object get(final String key) {

        return ehcacheUtils.ehCacheFactory.getCache(key).get(key).getObjectValue();
    }

    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public static Object keys(final String key) {

        return ehcacheUtils.ehCacheFactory.getCache(key).getKeys();
    }
}
