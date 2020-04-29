package com.liv.shiro.cache;

import com.liv.web.api.utils.RedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.cache
 * @Description:
 * @date 2020.4.25  22:33
 * @email 453826286@qq.com
 */
public class RedisShiroCache<K,V> implements Cache<K,V>  {
    private String prefix = "";

    /***一个星期**/
    private int exprieTimes = 604800;

    public RedisShiroCache(RedisCacheExprie e){
        this.prefix = e.getValue() + ":";
        this.exprieTimes = e.getExprieTimes()*60;   // 单位换算成 秒
    }

    @Override
    public V get(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        String strk = k( k);
        //更新key过期时间
        V v = (V) RedisUtils.get(strk);
        if(v!=null){
            RedisCacheExprie.refreshKeysExpries(strk);
        }
        return v;

    }

    @Override
    public V put(K k, V v) throws CacheException {
        if (k== null || v == null) {
            return null;
        }
        RedisUtils.set(k(k), v,exprieTimes);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if(k==null){
            return null;
        }
        V v = (V)RedisUtils.get(k(k));
        RedisUtils.del(k(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        RedisUtils.clear();
    }

    @Override
    public int size() {
        return RedisUtils.size();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>)RedisUtils.keys(prefix+"*");
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for(K k :keys){
            values.add(get(k));
        }
        return values;
    }

    private String k(K k){
        String kk = prefix+k.toString();
        return kk;
    }
}
