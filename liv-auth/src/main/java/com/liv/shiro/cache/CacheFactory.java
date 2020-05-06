package com.liv.shiro.cache;

import com.liv.utils.AppConst;
import com.liv.web.api.utils.LivContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.cache
 * @Description:
 * @date 2020.4.26  14:55
 * @email 453826286@qq.com
 */
public class CacheFactory {
    /**
     * 因为spring容器的配置文件加载顺序与业务所需不匹配，
     * 这里需要 判断一下是否已经读取了yml中的配置信息，使得自定义配置生效
     **/
    public static boolean cacheConfigInit = false;

    //Shiro 缓存 前缀
    public static final String PREFIX_SHIRO_CACHE = "storyweb-bp:cache:";
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "storyweb-bp:refresh_token:";

    //用户登录信息缓存
    public final static String SHIRO_AUTHENTICATIONCACHENAME = "shiro-cache:AuthenticationCacheName";
    public final static String SHIRO_AUTHORIZATIONCACHENAME = "shiro-cache:AuthorizationCacheName";

    //密码重试缓存
    public final static String PASSWORD_RETRY_CACHE = "shiro-cache:password-retry-cache";
    //用户登录
    public final static String LOGIN_SUCCESS_SUBJECT_CACHE = "shiro-cache:login-success-subject-cache";

    /**
     * @Author: LiV
     * @Date: 2020.4.26 14:59
     * @Description: 获取shiro的缓存管理里工具
     **/
    public static <K,V> Cache<K, V> getCache(String name){
        return getCacheManger().getCache(name);
    }
    /**
     * @Author: LiV
     * @Date: 2020.4.26 14:59
     * @Description: 获取shiro的缓存管理里工具
     **/
    public static CacheManager getCacheManger(){

        /***初始化一次 cache的配置，否则无法加载 yml中用户配置***/
        CacheManager cacheManager = LivContextUtils.getBean("shiroCacheManager",CacheManager.class);
        if(!cacheConfigInit){
            cacheConfigInit = true;
            //使用的redis
            if(cacheManager instanceof RedisCacheManager){
                //初始化枚举
                RedisCacheExprie.init();
            }
            //使用的ehcache
            if(cacheManager instanceof com.liv.shiro.cache.EhCacheManager){
                ((com.liv.shiro.cache.EhCacheManager)cacheManager).initCacheConfig();
            }
        }
        return cacheManager;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.26 14:59
     * @Description: 密码错误次数缓存
     **/
    public static <K,V> Cache<K, V> getPasswordRetryCache(){
        return getCacheManger().getCache(PASSWORD_RETRY_CACHE);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.26 14:59
     * @Description: 当前登录用户缓存
     **/
    public static <K,V> Cache<K, V> getLoginSuccessSubjectCache(){
        return getCacheManger().getCache(LOGIN_SUCCESS_SUBJECT_CACHE);
    }
}
