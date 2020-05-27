package com.liv.api.auth.shiro.stateless.filters;

import com.liv.api.auth.utils.ApiAuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.shiro.stateless.filters
 * @Description: 权限拦截器，判断有无权限
 * @date 2020.5.26  20:15
 * @email 453826286@qq.com
 */
public class PermissionsFilter  {

    public static void doCheckPermission(HttpServletRequest request){

    }
}
