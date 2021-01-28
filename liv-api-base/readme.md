> 项目介绍 
>> 项目通用配置的信息都在这里，通用的扩展也在这里.基础工程，含基本配置类、MVC基类、工具类、字典类、缓存类、表单校验类
* `springboot` 项目的基本配置包，集成此包即可实现简单的项目基本配置
* 样例工程参考 `x-sp`，此项目集成本包，只需要简单的几行代码即可实现增删改查


## 基本配置包：`config`
* `WebMvcConfiguration` 系统全局配置，跨域、返回值、静态资源、API接口、Request/Response
* `ValidateConfig` 表单校验配置
* `SwaggerConfig` API接口配置
* `PageHelperConfig` 分页配置
* `MybatisPlusConfig` MyBatis分页配置

## 基类包：`base`
* `BaseBean` `BaseQuery` Bean基类，分页配置，查询基类
* `BaseController` 控制器基类，service自动注入、校验、request、response、session获取
* `BaseService` 服务层基类，dao自动注入
* `DataBody` `ResultBody`  结果集Bean，分别为`data` `result`
* `CommonResultCode` 响应结果码

## 自定义注解包： `annotation`
* `ValidResult` `ValidResultInterceptor` 表单校验注解

## 缓存配置包 `cache`
* `ObjectByteRedisSerializer` `RedisConfig` Redis 缓存配置
* `EhCacheFactory`  EH 缓存配置

## 字典包 `dict` `dicttype`
`controller` `service`  `mapper` `query` `bean` 增删改查

## 异常包 `exception`
* `GlobalExceptionHandler` 全局异常处理
* `IBaseMessageInfo` 错误码、异常信息接口
* `InterfaceErrorController` 404异常处理
* `LivExceptionStatus` 异常接口
* `LivCommonException` 自定义异常
* `ValidateException` 自定义数据校验异常，交由global捕获

## 工具包 `util`
> `LivUtils` 工具类入口
* `CacheUtils` 缓存工具类
* `EhcacheUtils` ehcache 工具类
* `IPUtils` 获取ip工具类
* `LivCollectionUtils` 集合工具类
* `LivContextUtils` 上下文工具类
* `LivDateUtils` 日期工具类
* `LivPropertiesUtils` 自定义属性工具类
* `RedisUtils` 缓存工具类