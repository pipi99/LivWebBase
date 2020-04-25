//package com.liv.shiro.cache;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
///**
// * @author LiV
// * @Title:
// * @Package com.liv.shiro.cache
// * @Description: 缓存及清理菜单缓存
// * @date 2020.4.24  10:38
// * @email 453826286@qq.com
// */
//@Component
//@Aspect
//public class ResourceMenuCacheAspect extends BaseCacheAspect {
//
//
//    public ResourceMenuCacheAspect() {
//        setCacheName("sys-menuCache");
//    }
//
//
//    private String menusKeyPrefix = "menus-";
//
//
//    @Pointcut(value = "target(com.sishuok.es.sys.resource.service.ResourceService)")
//    private void resourceServicePointcut() {
//    }
//
//
//    @Pointcut(value = "execution(* save(..)) || execution(* update(..)) || execution(* delete(..))")
//    private void resourceCacheEvictAllPointcut() {
//    }
//
//
//    @Pointcut(value = "execution(* findMenus(*)) && args(arg)", argNames = "arg")
//    private void resourceCacheablePointcut(User arg) {
//    }
//
//
//    @Before(value = "resourceServicePointcut() && resourceCacheEvictAllPointcut()")
//    public void findRolesCacheableAdvice() throws Throwable {
//        clear();
//    }
//
//
//    @Around(value = "resourceServicePointcut() && resourceCacheablePointcut(arg)", argNames = "pjp,arg")
//    public Object findRolesCacheableAdvice(ProceedingJoinPoint pjp, User arg) throws Throwable {
//
//
//        User user = arg;
//
//
//        String key = menusKey(user.getId());
//        Object retVal = get(key);
//
//
//        if (retVal != null) {
//            log.debug("cacheName:{}, method:findRolesCacheableAdvice, hit key:{}", cacheName, key);
//            return retVal;
//        }
//        log.debug("cacheName:{}, method:findRolesCacheableAdvice, miss key:{}", cacheName, key);
//
//
//        retVal = pjp.proceed();
//
//
//        put(key, retVal);
//
//
//        return retVal;
//    }
//
//
//    public void evict(Long userId) {
//        evict(menusKey(userId));
//    }
//
//
//    private String menusKey(Long userId) {
//        return this.menusKeyPrefix + userId;
//    }
//
//
//}
