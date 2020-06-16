# 权限管理项目 
#### 一、token认证

    取消 session ，使用 jwttoken ,缓存到 cookie，每次访问刷新token。
    如果 无法缓存到 cookie或者是App 前端可根据登录返回的值和每次访问返回的 header,自行处理token，请求数据时带上即可
    因为session取消，subject需要与token绑定，每次访问的时候，系统自动根据token将对应的subject绑定到当前线程。
    shiro读取其他配置为空：https://blog.csdn.net/wuxuyang_7788/article/details/70141812
二、搭建参考网址：
    前端: https://d2.pub/zh/doc/d2-admin/     https://github.com/d2-projects
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

    缓存工具类:CacheFactory
    
   ###自定义配置项 ，引用此工程的话，有以下配置信息需要指定（开发环境下）,
   * 默认使用redis缓存，在引用的工程里需指定redis的配置信息；
   * 其他都可不写均为系统默认值，如需改动就在引用的工程里写相应的配置吧
   ```yaml
    
server:
  port: 80
  tomcat:
    uri-encoding: UTF-8

spring:
  profiles:
    active: dev
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/auth?useAffectedRows=true&useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
  redis:
    database: 0
    host: 127.0.0.1
    port: 6379
    password:
#    cluster:
#      nodes:
#        - 127.0.0.1:6379
#        - 127.0.0.1:6380
#        - 127.0.0.1:6381
#        - 127.0.0.1:6382
    lettuce:
      pool:
        # 连接池中的最大空闲连接 默认8
        max-idle: 8
        # 连接池中的最小空闲连接 默认0
        min-idle: 0
        # 连接池最大连接数 默认8 ，负数表示没有限制
        max-active: 8
        # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认-1
        max-wait: -1
    timeout: 30000

mybatis:
  config-location: classpath:mybatis/mybatis.cfg.xml        # mybatis配置文件所在路径
  mapper-locations: classpath:mybatis/mappers/**/*.xml       # mapper映射文件

token:
  #  token过期时间，单位秒
  tokenExpireTime: 604800 # token有效时长，7天，这个jwt的有效期，默认设置不用修改，不得小于 会话有效期
  #  token加密密钥
  secretKey: storywebkey
# 打印sql
logging:
  path: ./logs

liv:
  mapProps:
    usecache: ehcache  #值为 redis / ehcache,使用 redis还是ehcache
    user-login-fail-retry-times: 5   #用户登录失败重试次数
    user-login-fail-locked-time: 10   #用户登录失败锁定时常(分钟)
    user-login-timeouts: 1   #用户登录会话时常（分钟）
    user-login-token-renewal-onaccess: true  #值为 true / false, 用户每次访问是重新生成 token,也就是token用一次即失效
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

 # 权限过滤
  
   * 1.无需登即可访问，菜单类型设置为 open
   * 3.登录后授权访问,菜单类型设置为 perm
   * 4.无以上前缀的路径，登录后访问，设置为  login