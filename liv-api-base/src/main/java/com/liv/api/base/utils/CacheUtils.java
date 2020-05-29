package com.liv.api.base.utils;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.utils
 * @Description: 简单缓存通用工具
 * @date 2020.5.29  15:02
 * @email 453826286@qq.com
 */
@Component
public class CacheUtils {

    @Autowired
    private LivPropertiesUtils livPropertiesUtils;

    public  String USE_CACHE = "ehcache";

    @PostConstruct
    private void getCacheConst(){
        USE_CACHE = livPropertiesUtils.getMapProps().get("usecache")==null?USE_CACHE:livPropertiesUtils.getMapProps().get("usecache");
    }

    public void put(String key,Object value,long expireSeconds){
        if("ehcache".equals(USE_CACHE)){
            EhcacheUtils.set(key,value,expireSeconds);
        }else {
            RedisUtils.set( key, value,expireSeconds);
        }
    }

    public void put(String key,Object value){
        if("ehcache".equals(USE_CACHE)){
            EhcacheUtils.set(key,value);
        }else {
            RedisUtils.set( key, value);
        }
    }

    public Object get(String key){
        if("ehcache".equals(USE_CACHE)){
            return EhcacheUtils.get(key);
        }else {
            return RedisUtils.get( key);
        }
    }

    public void del(String key){
        if("ehcache".equals(USE_CACHE)){
            EhcacheUtils.del(key);
        }else {
            RedisUtils.del( key);
        }
    }
}
