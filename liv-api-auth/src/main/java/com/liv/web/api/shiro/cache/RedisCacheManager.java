package com.liv.web.api.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.cache
 * @Description:
 * @date 2020.4.25  23:10
 * @email 453826286@qq.com
 */
public class RedisCacheManager implements CacheManager {
    /**
     * @Author: LiV
     * @Date: 2020.4.27 16:56
     * @Description:
     **/
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new RedisShiroCache<K, V>(RedisCacheExprie.getInstance(name));
    }
}
