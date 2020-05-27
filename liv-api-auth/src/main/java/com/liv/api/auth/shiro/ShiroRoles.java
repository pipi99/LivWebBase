package com.liv.api.auth.shiro;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro
 * @Description: shiro角色常量类，系统默认的几种角色
 * @date 2020.5.13  17:00
 * @email 453826286@qq.com
 */
public interface ShiroRoles {
    /**超级管理员角色*/
    public static String SUPERMAN ="SUPERMAN";
    /**系统管理员角色*/
    public static String SYSMAN ="SYSMAN";
    /**用户角色*/
    public static String USER ="USER";
    /**访客角色*/
    public static String GUEST ="GUEST";

    /**匿名角色，某些特殊情况使用，默认每个用户都有*/
    public static String ANONYMOUS ="ANONYMOUS";


}
