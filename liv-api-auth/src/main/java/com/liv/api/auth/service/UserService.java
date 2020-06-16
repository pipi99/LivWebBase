package com.liv.api.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.domainmodel.UserDO;
import com.liv.api.auth.domainmodel.UserQuery;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.base.base.DataBody;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    public String dologin(UsernamePasswordToken token,HttpServletResponse response);
    public void logout();
    public UserDO getCurUser();
    public void refreshToken(HttpServletResponse response, String token);
    public void refreshToken(ShiroHttpServletResponse response, String token);
    public void clearCache(String userName);

    public Page<UserVO> pagelist(UserQuery query);
}
