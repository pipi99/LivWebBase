package com.liv.api.auth.utils;

import com.liv.api.auth.domainmodel.UserDO;
import com.liv.api.auth.service.UserService;
import com.liv.api.base.utils.LivContextUtils;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.utils
 * @Description: 用户工具类
 * @date 2020.5.13  18:20
 * @email 453826286@qq.com
 */
public class UserUtils {

    /**
     * @Author: LiV
     * @Date: 2020.5.13 18:22
     * @Description: 获取当前登录用户
     **/
    public static UserDO getCurrentUesr(){
        return LivContextUtils.getBean("userService", UserService.class).getCurUser();
    }
}
