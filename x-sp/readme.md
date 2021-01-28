> 项目介绍
* 开发环境：`springboot 2.2.5.RELEASE`、`jdk8`、`redis4`、`mysql5`
* 缓存支持： `ehcache` 、`redis`
* 权限框架： `shiro`
* 持久化框架： `mybatis-plus`、`mybatis`
* 校验框架： `hibernate-validator`
> 项目结构：
- 基础包（集成包）：`liv-api-base`
- 权限包（集成包）：`liv-api-auth`
- 权限管理（项目）：`liv-auth`
- 包使用样例介绍（本项目）：`x-sp`
# 一、创建项目
#### 一个简单的springboot项目，具备简单的增删改查功能
- 1、安装redis、mysql
- 2、创建maven项目，指定模板 maven-archetype-quickstart
- 3、添加源码包  `src/main/java` `src/main/resources`

# 二、配置文件
###（1）、 新建配置文件，在 `resource` 目录下
> `mybatis.cfg.xml`
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
</configuration>
```
> `mappers/SpMapper.xml` 约定文件名称与`dao` 名称一致,指定 `namespace` 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.liv.sp.dao.UserMapper">
</mapper>
```
###（2）、开发环境
> `profiles/dev/application.yml` 系统配置

```yaml
server:
  port: 81
  servlet:
    context-path: /x-sp
  tomcat:
    uri-encoding: UTF-8

spring:
  profiles:
    active: dev
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/sp?useAffectedRows=true&useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
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
  config-location: classpath*:mybatis/mybatis.cfg.xml        # mybatis配置文件所在路径
  mapper-locations: classpath*:mybatis/mappers/**/*.xml       # mapper映射文件
mybatis-plus:
  mapper-locations: classpath*:mybatis/mappers/**/*.xml

token:
  #  token过期时间，单位秒
  tokenExpireTime: 604800 # token有效时长，7天，这个jwt的有效期，默认设置不用修改，不得小于 会话有效期
  #  token加密密钥
  secretKey: storywebkey
# 打印sql
logging:
  path: ./sp/logs

liv:
  mapProps:
    default-password: 123456
    usecache: redis  #值为 redis / ehcache,使用 redis还是ehcache
    user-login-fail-retry-times: 5   #用户登录失败重试次数
    user-login-fail-locked-time: 10   #用户登录失败锁定时常(分钟)
    user-login-timeouts: 120   #用户登录会话时常（分钟）
    user-login-token-renewal-onaccess: true  #值为 true / false, 用户每次访问是重新生成 token,也就是token用一次即失效
```

> `profiles/dev/logback-spring.yml` 日志配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出 -->
<!-- scan:当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true -->
<!-- scanPeriod:设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。 -->
<!-- debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。 -->
<configuration  scan="true" scanPeriod="10 seconds">

    <!--<include resource="org/springframework/boot/logging/logback/base.xml" />-->

    <contextName>logback</contextName>

    <!-- 文件切割大小 -->
    <property name="maxFileSize" value="100MB" />
    <!-- 文档保留天数 -->
    <property name="maxHistory" value="60" />
    <!-- 文档保留总大小 -->
    <property name="totalSizeCap" value="10GB" />

    <!-- 配置参数logDir读取application.yml中的logging.path属性，如果没有配置，默认是只logs -->
    <springProperty scope="context" name="log.path" source="logging.path" defaultValue="logs" />

    <!-- 彩色日志 -->
    <!-- 彩色日志依赖的渲染类 -->
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
    <!-- 彩色日志格式 -->
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>


    <!--输出到控制台-->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <!--此日志appender是为开发使用，只配置最底级别，控制台输出的日志级别是大于或等于此级别的日志信息-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>debug</level>
        </filter>
        <encoder>
            <Pattern>${CONSOLE_LOG_PATTERN}</Pattern>
            <!-- 设置字符集 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>


    <!--输出到文件-->

    <!-- 时间滚动输出 level为 DEBUG 日志 -->
    <appender name="DEBUG_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_debug.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 日志归档 -->
            <fileNamePattern>${log.path}/debug/log-debug-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>${maxFileSize}</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>${maxHistory}</maxHistory>
            <totalSizeCap>${totalSizeCap}</totalSizeCap>
        </rollingPolicy>
        <!-- 此日志文件只记录debug级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>debug</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 时间滚动输出 level为 INFO 日志 -->
    <appender name="INFO_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_info.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!-- 每天日志归档路径以及格式 -->
            <fileNamePattern>${log.path}/info/log-info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>${maxFileSize}</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>${maxHistory}</maxHistory>
            <totalSizeCap>${totalSizeCap}</totalSizeCap>
        </rollingPolicy>
        <!-- 此日志文件只记录info级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>info</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!-- 时间滚动输出 level为 WARN 日志 -->
    <appender name="WARN_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_warn.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/warn/log-warn-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>${maxFileSize}</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>${maxHistory}</maxHistory>
            <totalSizeCap>${totalSizeCap}</totalSizeCap>
        </rollingPolicy>
        <!-- 此日志文件只记录warn级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>warn</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>


    <!-- 时间滚动输出 level为 ERROR 日志 -->
    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${log.path}/log_error.log</file>
        <!--日志文件输出格式-->
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset> <!-- 此处设置字符集 -->
        </encoder>
        <!-- 日志记录器的滚动策略，按日期，按大小记录 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/error/log-error-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>${maxFileSize}</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>${maxHistory}</maxHistory>
            <totalSizeCap>${totalSizeCap}</totalSizeCap>
        </rollingPolicy>
        <!-- 此日志文件只记录ERROR级别的 -->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <!--
        <logger>用来设置某一个包或者具体的某一个类的日志打印级别、
        以及指定<appender>。<logger>仅有一个name属性，
        一个可选的level和一个可选的addtivity属性。
        name:用来指定受此logger约束的某一个包或者具体的某一个类。
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
              还有一个特俗值INHERITED或者同义词NULL，代表强制执行上级的级别。
              如果未设置此属性，那么当前logger将会继承上级的级别。
        addtivity:是否向上级logger传递打印信息。默认是true。
    -->
    <!--<logger name="org.springframework.web" level="info"/>-->
    <!--<logger name="org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor" level="INFO"/>-->
    <!--
        使用mybatis的时候，sql语句是debug下才会打印，而这里我们只配置了info，所以想要查看sql语句的话，有以下两种操作：
        第一种把<root level="info">改成<root level="DEBUG">这样就会打印sql，不过这样日志那边会出现很多其他消息
        第二种就是单独给dao下目录配置debug模式，代码如下，这样配置sql语句会打印，其他还是正常info级别：
     -->
    <logger name="com.liv" level="debug" />
    <!--
        root节点是必选节点，用来指定最基础的日志输出级别，只有一个level属性
        level:用来设置打印级别，大小写无关：TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF，
        不能设置为INHERITED或者同义词NULL。默认是DEBUG
        可以包含零个或多个元素，标识这个appender将会添加到这个logger。
    -->


    <root level="info">
        <appender-ref ref="CONSOLE" />
<!--        <appender-ref ref="DEBUG_FILE" />-->
<!--        <appender-ref ref="INFO_FILE" />-->
<!--        <appender-ref ref="WARN_FILE" />-->
<!--        <appender-ref ref="ERROR_FILE" />-->
    </root>

    <!--生产环境:输出到文件-->
    <!--<springProfile name="pro">-->
    <!--<root level="info">-->
    <!--<appender-ref ref="CONSOLE" />-->
    <!--<appender-ref ref="DEBUG_FILE" />-->
    <!--<appender-ref ref="INFO_FILE" />-->
    <!--<appender-ref ref="ERROR_FILE" />-->
    <!--<appender-ref ref="WARN_FILE" />-->
    <!--</root>-->
    <!--</springProfile>-->

</configuration>
```
###（3）、生产环境
> `profiles/pro/application.yml`
 `profiles/pro/logback-spring.yml`
###（4）、测试环境
> `profiles/test/application.yml`
 `profiles/test/logback-spring.yml`
>
###（5）、 pom.xml配置
> 添加包引用 `liv-api-base` 
```xml
<dependency>
    <groupId>com.liv</groupId>
    <artifactId>liv-api-base</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
> 添加 `profiles` 

```xml
    <profiles>
        <profile>
            <!-- 声明这个profile的id身份 -->
            <id>dev</id>
            <!-- 默认激活：比如当mvn package命令是，没有传入参数，默认使用这个
                                        当使用mvn package -P dev 传入参数时，表示使用这个id的profile -->
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <!-- 该标签下配置对应的key  value -->
            <properties>
                <!-- 这里的标签名任意，在 项目的 properties、xml等配置文件中可以使用${profiles.active}取出dev这个值-->
                <profiles.active>dev</profiles.active>
            </properties>
        </profile>
        <profile>
            <id>test</id>
            <properties>
                <profiles.active>test</profiles.active>
            </properties>
        </profile>
        <profile>
            <id>pro</id>
            <properties>
                <profiles.active>pro</profiles.active>
            </properties>
        </profile>
    </profiles>
```
```xml
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
            <excludes>
                <exclude>profiles/**</exclude>
            </excludes>
            <includes>
                <include>**/*</include>
            </includes>
        </resource>
        <resource>
            <directory>src/main/resources/profiles/${profiles.active}</directory>
            <targetPath>${project.build.directory}/classes</targetPath>
        </resource>
    </resources>
```


# 三、Java源码样例

> （1）、启动类
```java
package com.liv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Author: LiV
 * @Date: 2020.12.11 11:28
 * @Description: 启动类
 **/
@EnableCaching
@SpringBootApplication
public class App extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
}

```


> （2）、业务类

* `Sp.java`
> 校验框架：http://www.360doc.com/content/07/0325/19/15643_413118.shtml，http://hibernate.org/validator/documentation/
>
```java
package com.liv.sp.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.dao.datamodel
 * @Description: java bean
 * @date 2020.12.11  10:52
 * @email 453826286@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("auth.sp")
@ApiModel(value="sp", description="java bean demo")
@EqualsAndHashCode(callSuper = false)
public class Sp  extends BaseBean<Sp> implements Serializable {

    private static final long serialVersionUID = 1L;

    /*主键*/
    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    @NotEmpty
    private Integer id;

    /*名称*/
    @NotNull
    @Length(min=10,max=100)
    @TableField(value = "NAME")
    private String name;

    /*说明*/
    @NotEmpty
    @Length(max=400)
    @TableField(value = "REMARK")
    private String remark;

    /*创建时间*/
    @TableField(value = "CREATE_TIME")
    private Date createTime;
}

```
* `SpQuery.java`
```java
package com.liv.sp.dao.datamodel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseQuery;
import org.apache.commons.lang3.StringUtils;

/**
 * @author LiV
 * @Title: 查询工具类
 * @Package com.liv.sp.dao.datamodel
 * @Description:
 * @date 2020.12.11  17:41
 * @email 453826286@qq.com
 */
public class SpQuery extends Sp implements BaseQuery<Sp> {
    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<Sp> getQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(this.getName())){
            qw.like("NAME",this.getName());
        }
        return qw;
    }
}

```
* `SpMapper.java`
```java
package com.liv.sp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.sp.dao.datamodel.Sp;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.dao.datamodel
 * @Description: Mapper
 * @date 2020.12.11  11:20
 * @email 453826286@qq.com
 */
@Mapper
public interface SPMapper extends BaseMapper<Sp> {
}

```

* `SpService.java`
```java
package com.liv.sp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.sp.dao.datamodel.Sp;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.service
 * @Description: 服务层
 * @date 2020.12.11  11:23
 * @email 453826286@qq.com
 */
public interface SpService extends IService<Sp> {
}

```

* `SpServiceImpl.java`
```java
package com.liv.sp.service.impl;

import com.liv.api.base.base.BaseService;
import com.liv.sp.dao.SpMapper;
import com.liv.sp.dao.datamodel.Sp;
import com.liv.sp.service.SpService;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.service.impl
 * @Description: 服务层实现
 * @date 2020.12.11  11:25
 * @email 453826286@qq.com
 */
public class SpServiceImpl extends BaseService<SpMapper, Sp> implements SpService {
}
```


* `SpServiceImpl.java`
```java
package com.liv.sp.service.impl;

import com.liv.api.base.base.BaseService;
import com.liv.sp.dao.SpMapper;
import com.liv.sp.dao.datamodel.Sp;
import com.liv.sp.service.SpService;
import org.springframework.stereotype.Service;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.service.impl
 * @Description: 服务层实现
 * @date 2020.12.11  11:25
 * @email 453826286@qq.com
 */
@Service
public class SpServiceImpl extends BaseService<SpMapper, Sp> implements SpService {
}

```

* `SpController.java`
```java
package com.liv.sp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.sp.dao.SpMapper;
import com.liv.sp.dao.datamodel.Sp;
import com.liv.sp.dao.datamodel.SpQuery;
import com.liv.sp.service.SpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.controller
 * @Description: 控制器
 * @date 2020.12.11  10:51
 * @email 453826286@qq.com
 */
@RestController
@RequestMapping(value = "/test")
@Api(tags = "测试控制器")
public class SpController  extends BaseController<SpMapper, Sp, SpService> {
    @ApiOperation(value = "根据ID查询", notes="根据给定的ID 查询")
    @ApiImplicitParam(name = "appId", value = "当前登录ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{appId}")
    public ResultBody getById(@PathVariable("appId") Long SpId) throws Exception {
        return ResultBody.success(service.getById(SpId));
    }

    @ApiOperation(value = "查询列表", notes="查询列表")
    @GetMapping(value="/list")
    public DataBody list() throws Exception {
        return DataBody.success(service.list());
    }

    @ApiOperation(value = "分页查询列表", notes="分页查询列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody SpQuery query) throws Exception {
        IPage<Sp> pageList = service.page(query.getPage(),query.getQueryWrapper());
        return DataBody.success(pageList);
    }

    @ApiOperation(value = "新增", notes="新增")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Sp d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新", notes="更新,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Sp d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除", notes="删除,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        boolean result = service.removeById(id);
        return ResultBody.success(result?"删除成功":"删除失败");
    }

    @ApiOperation(value = "批量删除", notes="删除,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }
}

```
####（3）、表结构
> 首先创建  liv-api-base 提供的权限表

```sql
drop table if exists sp;
create table sp 
(
   ID             bigint                         not null primary key auto_increment comment '主键',
   NAME           varchar(200)                   null comment '名称',
   REMARK         varchar(500)                   null comment '描述',
   CREATE_TIME    date                           null comment '创建日期'
) comment '表结构';
```
# 四、应该可以运行了
> 运行启动类 `App.java`
输出以下信息，启动成功
```text
2020-12-11 17:52:31.380  INFO 13632 --- [           main] o.a.coyote.http11.Http11NioProtocol      : Starting ProtocolHandler ["http-nio-81"]
2020-12-11 17:52:31.408  INFO 13632 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 81 (http) with context path '/x-sp'
2020-12-11 17:52:31.411  INFO 13632 --- [           main] com.liv.App                              : Started App in 8.113 seconds (JVM running for 10.61)
```
> 访问 http://localhost:81/x-sp/swagger-ui.html

> 访问 http://localhost:81/x-sp/doc.html

> 访问 http://localhost:81/x-sp/index.html