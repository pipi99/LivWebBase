package com.liv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.dao.datamodel.User;

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
    public boolean reg(User user);
    public User findByUserName(String username);
    public void unlock(String username);
    public void lock(String username);
    public String dologin(String username,String password);
    public void logout();
    public void reDologinSuccess(HttpServletResponse response, String token);
}
