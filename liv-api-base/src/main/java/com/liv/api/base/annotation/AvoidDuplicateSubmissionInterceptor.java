package com.liv.api.base.annotation;

import com.liv.api.base.base.ResultBody;
import com.liv.api.base.utils.CacheUtils;
import com.liv.api.base.utils.LivContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.annotation
 * @Description: 防重复提交注解
 * @date 2020.5.29  11:36
 * @email 453826286@qq.com
 */
@Aspect
@Component
@Slf4j
@Deprecated
public class AvoidDuplicateSubmissionInterceptor {

    @Autowired
    private CacheUtils cacheUtils;

    @Pointcut("@annotation(avoidDuplicateSubmission)")
    public void pointCut(AvoidDuplicateSubmission avoidDuplicateSubmission) {
    }

    @Around("pointCut(avoidDuplicateSubmission)")
    public Object around(ProceedingJoinPoint pjp, AvoidDuplicateSubmission avoidDuplicateSubmission) throws Throwable {
        int lockSeconds = avoidDuplicateSubmission.value();

        HttpServletRequest request = LivContextUtils.getRequest();
        Assert.notNull(request, "request can not null");

        //token
        Subject subject = SecurityUtils.getSubject();
        String token = subject.getPrincipal().toString();
        String path = request.getServletPath();
        String clientId = getClientId();
        String key = getKey(token, path);


        Object lock = cacheUtils.get(key);
        log.debug("=====有锁？===="+lock);
        if (lock==null) {
            log.debug("=====上锁===="+key);
            cacheUtils.put(key,clientId,lockSeconds);
            // 获取锁成功
            Object result;

            try {
                // 执行进程
                result = pjp.proceed();
            } finally {
                // 解锁
                log.debug("=====解锁===="+key);
                cacheUtils.del(key);
            }

            return result;

        } else {
            log.debug("=====锁定 （重复请求）===="+key);
            return ResultBody.success("重复请求，请稍后再试");
        }

    }

    private String getKey(String token, String path) {
        return token + path;
    }

    private String getClientId() {
        return UUID.randomUUID().toString();
    }
}
