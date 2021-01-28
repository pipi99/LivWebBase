package com.liv.api.base.interceptor;

import com.liv.api.base.utils.ReflectHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.interceptor
 * @Description: sql 拼截
 * @date 2020.12.28  21:35
 * @email 453826286@qq.com
 */
@Intercepts({
//        @Signature(
//                type = StatementHandler.class,
//                method = "prepare",
//                args = {Connection.class, Integer.class}
//        ),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        //查询
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class MyBatisSqlInterceptor  implements Interceptor {
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    private Properties properties;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        try {

            MappedStatement mappedStatement = (MappedStatement) args[0];
            Object parameter = args[1];

            BoundSql boundSql = mappedStatement.getBoundSql(parameter);

            String origSql = boundSql.getSql();

            String mSql = origSql.replaceAll("@\\[dbschema\\]",properties.getProperty("schema","")+".");

            resetSql2Invocation(invocation, mSql);
        } catch (Exception e) {
//                e.printStackTrace();
            // 任何一个地方有异常，都去执行原始操作
            // ignore
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * 将sql反射回方法中
     *
     * @param invocation Invocation
     * @param sql        sql
     */
    private void resetSql2Invocation(Invocation invocation, String sql) {
        final Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        MappedStatement newMappedStatement = generalMappedStatement(statement, new BoundSqlSqlSource(boundSql));
        // MetaObject mybatis里面提供的一个工具类，类似反射的效果
        MetaObject msObject = MetaObject.forObject(newMappedStatement, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
        msObject.setValue("sqlSource.boundSql.sql", sql);
        args[0] = newMappedStatement;
    }

    /**
     * 创建新的 MappedStatement
     *
     * @param mappedStatement MappedStatement
     * @param sqlSource       SqlSource
     * @return MappedStatement
     */
    private MappedStatement generalMappedStatement(MappedStatement mappedStatement, SqlSource sqlSource) {
        MappedStatement.Builder builder =
                new MappedStatement.Builder(mappedStatement.getConfiguration(), mappedStatement.getId(), sqlSource, mappedStatement.getSqlCommandType());
        builder.resource(mappedStatement.getResource());
        builder.fetchSize(mappedStatement.getFetchSize());
        builder.statementType(mappedStatement.getStatementType());
        builder.keyGenerator(mappedStatement.getKeyGenerator());
        if (mappedStatement.getKeyProperties() != null && mappedStatement.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : mappedStatement.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(mappedStatement.getTimeout());
        builder.parameterMap(mappedStatement.getParameterMap());
        builder.resultMaps(mappedStatement.getResultMaps());
        builder.resultSetType(mappedStatement.getResultSetType());
        builder.cache(mappedStatement.getCache());
        builder.flushCacheRequired(mappedStatement.isFlushCacheRequired());
        builder.useCache(mappedStatement.isUseCache());

        return builder.build();
    }

    private class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
