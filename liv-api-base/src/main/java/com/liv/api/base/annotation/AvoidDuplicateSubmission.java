package com.liv.api.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.annotation
 * @Description: 防重复提交注解
 * @date 2020.5.29  11:36
 * @email 453826286@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface AvoidDuplicateSubmission {
    //锁时常  秒
    int value() default  5;
}
