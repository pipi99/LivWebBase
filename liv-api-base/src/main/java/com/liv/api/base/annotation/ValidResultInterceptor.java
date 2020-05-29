package com.liv.api.base.annotation;

import com.google.common.collect.Lists;
import com.liv.api.base.exception.ValidateException;
import com.liv.api.base.utils.LivContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.annotation
 * @Description: 校验解决注解处理器
 * @date 2020.4.19  14:45
 * @email 453826286@qq.com
 */

@Aspect
@Component
@Slf4j
public class ValidResultInterceptor {
    public ValidResultInterceptor() {
    }

    @Pointcut("@annotation(com.liv.api.base.annotation.ValidResult)")
    private void anyMethod() {
    }

    @Around("anyMethod()")
    public Object checkRequestHead(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] o = joinPoint.getArgs();
        for (Object o1 : o) {
            if(o1 instanceof BindingResult){
                BindingResult result = (BindingResult)o1;
                if(result.hasErrors()){
                    List<String> msg = Lists.newArrayList();
                    for (ObjectError error : result.getAllErrors()) {
                        msg.add(error.getDefaultMessage());
                    }
                    LivContextUtils.getRequest().setAttribute("msg",msg);
                    //交给系统处理异常
                    throw new ValidateException();
                }
            }
        }
       return joinPoint.proceed();
    }

}
