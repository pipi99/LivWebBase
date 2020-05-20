package com.liv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.api.auth.domainmodel.UserDO;
import com.liv.web.api.dao.datamodel.User;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.http.HttpServletResponse;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface UserService extends IService<User>{
}
