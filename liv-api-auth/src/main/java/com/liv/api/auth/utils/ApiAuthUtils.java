package com.liv.api.auth.utils;

import com.liv.api.auth.domainmodel.UserDO;
import com.liv.api.auth.service.UserService;
import com.liv.api.base.utils.LivContextUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.utils
 * @Description: 用户工具类
 * @date 2020.5.13  18:20
 * @email 453826286@qq.com
 */
public class ApiAuthUtils {

    private static HttpServletRequest request;
    /**
     * @Author: LiV
     * @Date: 2020.5.13 18:22
     * @Description: 获取当前登录用户
     **/
    public static UserDO getCurrentUser(){
        return LivContextUtils.getBean("apiUserService", UserService.class).getCurUser();
    }

    public static ApiAuthUtils getInstance(HttpServletRequest req){
        request = req;
        return new ApiAuthUtils();
    }
    /**
     * @Author: LiV
     * @Date: 2020.5.25 16:45
     * @Description: 截取URL （http://localhost:8080/livauth/auth/aaa?userid=2  截取为 auth/aaa）
     * 结果作为 permission 模块字符串
     **/
    public String getPermissionStrByUrl(String url){
        if(StringUtils.isEmpty(url)){
            return "";
        }
        if(url.indexOf("://")!=-1){
            url = url.substring(url.indexOf("://")+4);
            url = url.substring(url.indexOf("/")+1);
        }
        if(url.indexOf("?")!=-1){
            url = url.substring(0,url.indexOf("?"));
        }

        if(url.startsWith("/")){
            url = url.substring(url.indexOf("/")+1);
        }

        return url.replaceAll("/","_");
    }


}
