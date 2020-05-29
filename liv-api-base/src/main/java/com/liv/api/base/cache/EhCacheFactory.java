package com.liv.api.base.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.stereotype.Component;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.cache
 * @Description: 自定义EhcacheManager
 * @date 2020.4.29  15:44
 * @email 453826286@qq.com
 */
@Component
public class EhCacheFactory {

    //不指定过期时间的，默认用这个，一星期
    public  long  EXPIRE_SECONDS = 604800;

    private String EHCACHE_INSTANCES = "EHCACHE_INSTANCES";
    private Cache  cacheInstances;
    private EhCacheFactory(){
        initCaches();
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.29 11:56
     * @Description: 根据名称和过期时间创建缓存
     **/
    private Cache createCache(String name,long expireSeconds){
        Configuration configuration = new Configuration();
        configuration.diskStore(new DiskStoreConfiguration().path("java.io.tmpdir"));

        CacheConfiguration cacheConfiguration = new CacheConfiguration(name, 100000)//缓存名称(必须唯一),maxElements内存最多可以存放的元素的数量
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)//清理机制：LRU最近最少使用 FIFO先进先出 LFU较少使用
                .eternal(false)//元素是否永久缓存
                .timeToIdleSeconds(expireSeconds)//元素最大闲置时间
                .timeToLiveSeconds(604800)//元素最大生存时间 // 7天
                .diskExpiryThreadIntervalSeconds(120)//缓存清理时间(默认120秒)
                //LOCALTEMPSWAP当缓存容量达到上限时，将缓存对象（包含堆和非堆中的）交换到磁盘中
                //NONE当缓存容量达到上限时，将缓存对象（包含堆和非堆中的）交换到磁盘中
                //DISTRIBUTED按照_terracotta标签配置的持久化方式执行。非分布式部署时，此选项不可用
                .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE)).maxEntriesLocalDisk(0);//磁盘中最大缓存对象数0表示无穷大)

        configuration.addCache(cacheConfiguration);

        CacheManager manager = CacheManager.create(configuration);
        Cache cache = manager.getCache(name);//获得缓存
        return cache;
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.29 13:57
     * @Description: 将缓存实例 缓存起来
     **/
    private void initCaches(){
       this.cacheInstances = createCache(EHCACHE_INSTANCES,604800);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.29 17:04
     * @Description: 根据key获取缓存实例
     **/
    public Cache getCache(String key,long expireSeconds){
        String cachename = (String)cacheInstances.get(key).getObjectValue();
        if(cachename==null){
            cachename = EHCACHE_INSTANCES+":instance:"+expireSeconds;
            //缓存key 与cache 关系
            Element element = new Element(key, cachename);
            cacheInstances.put(element);
        }
        Cache cache = (Cache)cacheInstances.get(cachename).getObjectValue();
        //创建缓存实体
        if(cache==null){
            //缓存cache
            cache = createCache( cachename, expireSeconds);
            Element element = new Element(cachename, cache);
            cacheInstances.put(element);
        }
        return cache;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.29 17:04
     * @Description: 根据key获取缓存实例
     **/
    public Cache getCache(String key){
        String cachename = (String)cacheInstances.get(key).getObjectValue();
        if(cachename==null){
            cachename = EHCACHE_INSTANCES+":instance:"+EXPIRE_SECONDS;
            //缓存key 与cache 关系
            Element element = new Element(key, cachename);
            cacheInstances.put(element);
        }
        Cache cache = (Cache)cacheInstances.get(cachename).getObjectValue();
        //创建缓存实体
        if(cache==null){
            //缓存cache
            cache = createCache( cachename, EXPIRE_SECONDS);
            Element element = new Element(cachename, cache);
            cacheInstances.put(element);
        }
        return cache;
    }


}
