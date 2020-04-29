package com.liv.shiro.cache;

import com.liv.shiro.stateless.jwt.JwtUtil;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.*;
import net.sf.ehcache.distribution.RMICacheManagerPeerListenerFactory;
import net.sf.ehcache.distribution.RMICacheManagerPeerProviderFactory;
import net.sf.ehcache.distribution.RMICacheReplicatorFactory;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.apache.shiro.cache.Cache;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.cache
 * @Description: 自定义EhcacheManager
 * @date 2020.4.29  15:44
 * @email 453826286@qq.com
 */
public class EhCacheManager extends org.apache.shiro.cache.ehcache.EhCacheManager {
    public EhCacheManager (){
        super();
    }

    //https://my.oschina.net/u/913265/blog/892923
    /**
     * @Author: LiV
     * @Date: 2020.4.29 17:04
     * @Description: 配置文件加载完成后需调用一次，否则无法获取到配置文件的配置数据
     **/
    public void initCacheConfig(){
        Configuration configuration = new Configuration();
        configuration.diskStore(new DiskStoreConfiguration().path("java.io.tmpdir"));

        //根据yml初始化 ehcache
        for (RedisCacheExprie fsEnum : RedisCacheExprie.values()) {
            String name = fsEnum.getValue();
            int expireTime = fsEnum.getExprieTimes();

            CacheConfiguration cacheConfiguration = new CacheConfiguration(name, 10000)//缓存名称(必须唯一),maxElements内存最多可以存放的元素的数量
                    .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)//清理机制：LRU最近最少使用 FIFO先进先出 LFU较少使用
                    .eternal(false)//元素是否永久缓存
                    .timeToIdleSeconds(expireTime*60)//元素最大闲置时间
                    .timeToLiveSeconds(604800)//元素最大生存时间 // 7天
                    .diskExpiryThreadIntervalSeconds(120)//缓存清理时间(默认120秒)
                    //LOCALTEMPSWAP当缓存容量达到上限时，将缓存对象（包含堆和非堆中的）交换到磁盘中
                    //NONE当缓存容量达到上限时，将缓存对象（包含堆和非堆中的）交换到磁盘中
                    //DISTRIBUTED按照_terracotta标签配置的持久化方式执行。非分布式部署时，此选项不可用
                    .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.NONE)).maxEntriesLocalDisk(0);//磁盘中最大缓存对象数0表示无穷大)

            configuration.addCache(cacheConfiguration);
        }

        CacheManager manager = CacheManager.create(configuration);

        this.setCacheManager(manager);
//        Cache cache = manager.getCache("metaCache");//获得缓存
    }
}
