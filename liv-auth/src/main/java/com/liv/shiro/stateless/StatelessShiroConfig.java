package com.liv.shiro.stateless;

import com.google.common.collect.Lists;
import com.liv.shiro.cache.CacheFactory;
import com.liv.shiro.cache.RedisCacheManager;
import com.liv.shiro.realms.UserCacheRealm;
import com.liv.shiro.realms.UserRealm;
import com.liv.shiro.stateless.filters.StatelessAuthcFilterFactoryBean;
import com.liv.shiro.stateless.jwt.JwtProperties;
import com.liv.utils.AppConst;
import com.liv.utils.LivPropertiesUtils;
import com.liv.utils.PasswordHelper;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.Resource;
import javax.servlet.DispatcherType;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description: shiro配置
 * @date 2020.4.19  20:17
 * @email 453826286@qq.com
 */

@Configuration
@EnableConfigurationProperties({JwtProperties.class, LivPropertiesUtils.class})
public class StatelessShiroConfig {
    @Autowired
    private LivPropertiesUtils livPropertiesUtils;

    /**
     * @Author: LiV
     * @Date: 2020.4.23 09:01
     * @Description: 执行 SecurityUtils.setManager
     **/
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setArguments(securityManager());
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        return bean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() throws Exception {

        /**使用FilterRegistrationBean来对Filter进行自定义注册*/
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        /**
         * DelegatingFilterProxy就是一个对于 filter的代理，用这个类的好处主要是通过Spring容器来管理 filter的生命周期，
         * 还有就是如果filter中需要一些Spring容器的实例，可以通过spring直接注入，
         * 另外读取一些配置文件这些便利的操作都可以通过Spring来配置实现
         * DelegatingFilterProxy根据targetBeanName从Spring 容器中获取被注入到Spring 容器的Filter实现类
         *
         * 因为filter比bean先加载，也就是spring会先加载filter指定的类到Container中，这样filter中注入的spring bean就为null了。
         * 解决办法：先filter中加入DelegatingFilterProxy类，"targetFilterLifecycle"指明作用于filter的所有生命周期。原理是，DelegatingFilterProxy类是一个代理类，所有的请求都会首先发到这个filter代理，然后再按照"filter-name"委派到spring中的这个bean。
         **/
        DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
        filterProxy.setTargetBeanName("stateLessShiroFilter");
        filterProxy.setTargetFilterLifecycle(true); //设置为true 可以在stateLessShiroFilter 中可以使用spring的bean

        filterRegistrationBean.setFilter(filterProxy);
        /**
         * 在Spring Boot中使用Shiro，提供了自定义的Filter来进行权限控制，同时，希望该自定义Filter能作为Spring Bean，以便获得Spring Bean的相关益处，比如可以使用@Autowired进行Bean的注入等。
         * 自定义Filter通过@Bean注解后，被Spring Boot自动注册到了容器的Filter chain中，这样导致的结果是，所有URL都会被自定义Filter过滤，而不是Shiro中配置的一部分URL。
         * Spring Boot对Filter，Servlet提供了相应的注册类，来进行精细化的配置，我们可以使用注册类来取消Filter的自动注册。
         * 通过使用FilterRegistrationBean来进行Filter的注册，同时，设置enabled为false，就可以取消Filter的自动注册行为了。
         * 参考：https://www.jianshu.com/p/bf79fdab9c19
         */

        //==================================
         /** 经过测试，以上解释不太准确，就算为false,springbean管理的filter,还是会被注册进入 web链。
         * 切记 ***shiro filer必须在ShiroFilterFactoryBean 中 new ***
         **/
        filterRegistrationBean.setEnabled(false);

        filterRegistrationBean.addUrlPatterns("/*"); //过滤规则，即所有的请求
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistrationBean;
    }


    /*** 这个即是上边调用的shiroFilter过滤器，也就是shiro配置的过滤器*/
    @Bean(name = "stateLessShiroFilter")
    public ShiroFilterFactoryBean stateLessShiroFilter()  {return StatelessAuthcFilterFactoryBean.getShiroFilterFactoryBean(securityManager()); }

    /**
     * @Author: LiV
     * @Date: 2020.4.23 13:53
     * @Description: 权限注解支持
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor AuthorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }
    /**
     * @see org.apache.shiro.mgt.SecurityManager
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setCacheManager(cacheManager());

        //在全局级别为所有Subject 禁用Session
        DefaultSubjectDAO dao = (DefaultSubjectDAO)manager.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator)dao.getSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);

        manager.setSubjectFactory(defaultWebSubjectFactory());
        manager.setSessionManager(defaultSessionManager());

        //多realm配置
        manager.setAuthenticator(ModularRealmAuthenticator());
        manager.setAuthorizer(ModularRealmAuthorizer());
        return manager;
    }

    //===================realm配置====================
    /**
     * @Author: LiV
     * @Date: 2020.4.23 17:13
     * @Description: 多Reaml配置
     **/
    @Bean
    public ModularRealmAuthenticator ModularRealmAuthenticator(){
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();

        /**所有的 realm 都会验证，其中一个成功，也会继续验证后面的 realm，最后返回成功*/
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        authenticator.setRealms(getRealms());
        return authenticator;
    }
    /**
     * @Author: LiV
     * @Date: 2020.4.23 17:13
     * @Description: 多Reaml配置
     **/
    @Bean
    public ModularRealmAuthorizer ModularRealmAuthorizer(){
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setRealms(getRealms());
        return authorizer;
    }
    private List<Realm> getRealms(){
        /**所有的 realm 都会验证，其中一个成功，也会继续验证后面的 realm，最后返回成功*/
        List<Realm> realms = Lists.newArrayList(/*userRealm(),*/userCacheRealm());
        return realms;
    }
    //===================realm配置====================

    /**
      * @see DefaultWebSessionManager
      * @return 无状态下的会话管理器
      */
    @Bean(name="sessionManager")
    public DefaultSessionManager defaultSessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    /**
     * @see DefaultWebSessionManager
     * @return 无状态下的SubjectFactory
     */
    @Bean
    public DefaultWebSubjectFactory defaultWebSubjectFactory() { return new StatelessDefaultSubjectFactory(); }

    /**
     * @see UserRealm--->AuthorizingRealm
     * @return
     */
    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
        UserRealm statelessRealm = new UserRealm();
        statelessRealm.setCredentialsMatcher(credentialsMatcher());
        statelessRealm.setCachingEnabled(false);
        statelessRealm.setCacheManager(cacheManager());
        return statelessRealm;
    }

    /**
     * @see UserRealm--->AuthorizingRealm
     * @return
     */
    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public UserCacheRealm userCacheRealm() {
        UserCacheRealm realm = new UserCacheRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        realm.setCachingEnabled(true);
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthorizationCacheName(CacheFactory.SHIRO_AUTHORIZATIONCACHENAME);
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthenticationCacheName(CacheFactory.SHIRO_AUTHENTICATIONCACHENAME);
        realm.setCacheManager(cacheManager());
        return realm;
    }


    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {return new LifecycleBeanPostProcessor();}

    /**
     * @Author: LiV
     * @Date: 2020.4.23 11:08
     * @Description: //这里的规则需要与  passwordHelper中 新增用户的密码加密规则 一致  ,用户密码校验的 时候用
     **/
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() { return PasswordHelper.getMatcher(cacheManager()); }


    /***缓存配置 -redis还是ehcache====读取application.yml中的配置====================================**/

    @Bean(name = "shiroCacheManager")
    public CacheManager cacheManager(){
        String usecache = livPropertiesUtils.getMapProps().get("usecache");
        if("redis".equals(usecache)){
            return new RedisCacheManager();
        }else{
//            EhCacheManager cacheManager = new EhCacheManager();
//            cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
            //这里使用自定义的ehcachemanager,确保能读取 application中的过期时间配置
            com.liv.shiro.cache.EhCacheManager cacheManager = new com.liv.shiro.cache.EhCacheManager();
            return cacheManager;
        }

    }

    /**
     * 设置为共享模式
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }



}