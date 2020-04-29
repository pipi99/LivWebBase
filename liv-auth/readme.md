# 权限管理项目 
#### 一、token认证

    取消 session ，使用 jwttoken ,缓存到 cookie，每次访问刷新token。
    如果 无法缓存到 cookie或者是App 前端可根据登录返回的值和每次访问返回的 header,自行处理token，请求数据时带上即可
    因为session取消，subject需要与token绑定，每次访问的时候，系统自动根据token将对应的subject绑定到当前线程。
    shiro读取其他配置为空：https://blog.csdn.net/wuxuyang_7788/article/details/70141812
二、搭建参考网址：
  
    中文教程：http://www.w3cschool.cn/shiro/ac781ife.html
    加密：https://www.cnblogs.com/mozq/p/11761896.html
    按钮控制：https://blog.csdn.net/kity9420/article/details/102330886

三、常用权限注解
    
    
    @RequiresAuthentication
    
    表示当前 Subject 已经通过 login 进行了身份验证；即 Subject.isAuthenticated() 返回 true。 
    
    @RequiresUser 
    
    表示当前 Subject 已经身份验证或者通过记住我登录的。 
    
    @RequiresGuest
    
    表示当前 Subject 没有身份验证或通过记住我登录过，即是游客身份。 
    
    @RequiresRoles(value={“admin”, “user”}, logical= Logical.AND)
    
    表示当前 Subject 需要角色 admin 和 user。 
    
    @RequiresPermissions (value={“user:a”, “user:b”}, logical= Logical.OR)
    
    表示当前 Subject 需要权限 user：a 或 user：b。 

开发说明：

    缓存工具类:com.liv.shiro.cache.CacheFactory
    
   ###自定义配置项
   ```yaml
    
liv:
  mapProps:
    usecache: redis  # redis  或者 ehcache,使用 redis还是ehcache
    user-login-fail-retry-times: 2   #用户登录失败重试次数
    user-login-fail-locked-time: 1   #用户登录失败锁定时常(分钟)
    user-login-timeouts: 1   #用户登录会话时常（分钟）
    user-login-token-renewal-onaccess: true  #用户每次访问是重新生成 token
   ```
    

 包路径说明：
 controller : 控制器
 controller.viewmodel: 视图模型
 service: 服务接口
 service.impl： 服务实现
 service.domainmodel: 领域模型
 dao: 数据操作
 dao.datamodel: 数据模型
 util: 工具包

 
 