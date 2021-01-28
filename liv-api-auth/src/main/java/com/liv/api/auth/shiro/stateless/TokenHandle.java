package com.liv.api.auth.shiro.stateless;

import com.liv.api.auth.shiro.cache.CacheFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.shiro.stateless
 * @Description: token 过期处理 ,如果token策略设置为每次都生成新的，已过期token不会立即删除，避免出现token失效用户重新登录的情况
 * @date 2020.6.15  10:07
 * @email 453826286@qq.com
 */
@Slf4j
public class TokenHandle {
    //token过期处理毫秒数  15秒后清理无用token
    private static Long expireTime = 15000L;
    //maximumPoolSize设置为1 ，DiscardPolicy策略：该策略会默默丢弃无法处理的任务，不予任何处理。当然使用此策略，业务场景中需允许任务的丢失；
    private static ExecutorService pool  = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());;

    //存储token及访问时间
    private static Map<String, Long> map = new ConcurrentHashMap<>();
    public static void clearToken(String token){
//        log.debug("==========>准备清理:"+token);
        map.put(token,System.currentTimeMillis());
        startTokenClear();
    }

    private  static void startTokenClear(){
        pool.execute(new TokenClear());
    }

    static class TokenClear implements Runnable{

        @Override
        public void run() {

            try{
                while (exeClear()){

                    Thread.sleep(2000);
                }
//                log.debug("==========>清理完毕:");
//                log.debug("==========>清理线程名称try:"+Thread.currentThread().getName());
                throw new InterruptedException();
            }catch (InterruptedException e) {
//                e.printStackTrace();
                //中断状态在抛出异常前，被清除掉，因此在此处重置中断状态
//                log.debug("==========>清理线程名称catch:"+Thread.currentThread().getName());
                Thread.currentThread().interrupt();
            }
        }

        private boolean exeClear(){
            Set<Map.Entry<String, Long>> es = map.entrySet();
            Iterator<Map.Entry<String, Long>> it = es.iterator();
            Map.Entry<String, Long> entry = it.next();
            Long value = entry.getValue();
            if(System.currentTimeMillis()-value>=expireTime){
                map.remove(entry.getKey());
//                log.debug("==========>清理token:"+entry.getKey());
                //清除已过期token
                CacheFactory.getLoginSuccessSubjectCache().remove(entry.getKey());
            }
            return map.size()>0;
        }
    }
}
