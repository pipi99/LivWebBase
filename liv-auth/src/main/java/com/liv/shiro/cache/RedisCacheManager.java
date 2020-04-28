package com.liv.shiro.cache;

import com.liv.utils.AppConst;
import com.liv.web.api.utils.RedisUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.TimeUnit;

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
        return new ShiroRedisCache<K, V>(RedisCacheManager.REDIS_CACHE_EXPRIE.getInstance(name));
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.27 14:34
     * @Description: 如果是使用redis的缓存时常
     **/
    static enum REDIS_CACHE_EXPRIE{
        //如果没有指定过期时间，默认为一个星期（其实，shiro能用到的应该都在了）
        COMMON("shiro-cache:common",604800),

        //密码错误锁定时常
        PASSWORD_RETRY_CACHE(CacheFactory.PASSWORD_RETRY_CACHE, AppConst.USER_LOGIN_FAIL_LOCKED_TIME),
        //用户登录缓存时常
        LOGIN_SUCCESS_SUBJECT_CACHE(CacheFactory.LOGIN_SUCCESS_SUBJECT_CACHE, AppConst.USER_LOGIN_TIMEOUTS),
        //用户登录信息缓存时常
        SHIRO_AUTHENTICATIONCACHENAME(CacheFactory.SHIRO_AUTHENTICATIONCACHENAME, AppConst.USER_LOGIN_TIMEOUTS),
        SHIRO_AUTHORIZATIONCACHENAME(CacheFactory.SHIRO_AUTHORIZATIONCACHENAME, AppConst.USER_LOGIN_TIMEOUTS);

        private String value;//缓存名称
        private int exprieTimes;//过期时间

        public String getValue(){
            return this.value;
        }

        public void setValue(String value){
            this.value = value;
        }

        public int getExprieTimes(){
            return this.exprieTimes; //分钟
        }

        public void setExprieTimes(int exprieTimes){
            this.exprieTimes = exprieTimes; //分钟
        }
        /**
         * 按照Value获得枚举值
         */
        public static REDIS_CACHE_EXPRIE getInstance(String value) {

            if (value != null) {
                for (REDIS_CACHE_EXPRIE fsEnum : REDIS_CACHE_EXPRIE.values()) {
                    if (fsEnum.getValue() .equals(value)) {
                        return fsEnum;
                    }
                }
            }
            COMMON.setValue(value);
            return COMMON;
        }

        /**更新缓存key的过期时间为初始值*/
        public static void refreshKeysExpries(String... keys){
            for(int i=0;i<keys.length;i++){
                String key= keys[i];
                for (REDIS_CACHE_EXPRIE fsEnum : REDIS_CACHE_EXPRIE.values()) {
                    if (key.indexOf(fsEnum.getValue())!=-1) {
                        //更新key过期时间
                        RedisUtils.expire(key,fsEnum.getExprieTimes(), TimeUnit.MINUTES);
                        break;
                    }
                }
            }
        }
        // 构造方法
        private REDIS_CACHE_EXPRIE(String name, int index) { this.value = name; this.exprieTimes = index; }
    }
}
