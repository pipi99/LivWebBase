package com.liv.api.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.liv.api.auth.dao.UserGroupMapper;
import com.liv.api.auth.dao.UserRoleMapper;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.dao.datamodel.UserGroup;
import com.liv.api.auth.dao.datamodel.UserRole;
import com.liv.api.auth.domainmodel.UserDO;
import com.liv.api.auth.domainmodel.UserQuery;
import com.liv.api.auth.service.RoleService;
import com.liv.api.auth.shiro.ShiroRoles;
import com.liv.api.auth.shiro.realms.UserCacheRealm;
import com.liv.api.auth.shiro.stateless.jwt.JwtUtil;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.shiro.cache.CacheFactory;
import com.liv.api.auth.shiro.realms.RetryLimitHashedCredentialsMatcher;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.auth.utils.PasswordHelper;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.base.base.BaseService;
import com.liv.api.base.utils.LivContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("apiUserService")
class UserServiceImpl extends BaseService<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;
    /**
     * @Author: LiV
     * @Date: 2020.4.22 14:08
     * @Description: 用户登录，返回token
     **/
    @Override
    public String dologin(String username, String password) {
        Subject user = SecurityUtils.getSubject();
        //清除缓存
        clearCache(username);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        //执行登录
        user.login(usernamePasswordToken);
        return dologinSuccess( LivContextUtils.getResponse()) ;
    }

    /**
     * @Author: LiV
     * @Date: 2020.12.28 15:42
     * @Description: swagger的接口调试，支持随意用户名登录，用户不存在自动创建一个
     **/
    @Override
    public String dologin(UsernamePasswordToken token,HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        //清除缓存
        clearCache(token.getUsername());

        //////swagger的接口调试，支持随意用户名登录，用户不存在自动创建一个
        User user = this.findByUserName(token.getUsername());
        if(user==null){
            user = new User();
            user.setUserName(token.getUsername());
            user.setAlias(token.getUsername());
            user.setPassword(new String(token.getPassword()));
            user.setTemp("1");
            this.reg(user);
        }

        //执行登录
        subject.login(token);
        return dologinSuccess( response) ;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.27 16:02
     * @Description: 登录成功的操作
     **/
    private String dologinSuccess(HttpServletResponse response) {

        String jwttoken = JwtUtil.sign();
        //返回token到reponse
        JwtUtil.cookieHeaderToken(response,jwttoken);
        return jwttoken;
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.26 17:14
     * @Description: 清空用户相关缓存
     **/
    public void clearCache(String userName){
        //清空认证缓存
        SimplePrincipalCollection principals = new SimplePrincipalCollection(userName,UserCacheRealm.REAL_NAME);
        CacheFactory.getCache(CacheFactory.SHIRO_AUTHORIZATIONCACHENAME).remove(principals);
        CacheFactory.getCache(CacheFactory.SHIRO_AUTHENTICATIONCACHENAME).remove(principals);

        //清空 角色权限缓存
        Cache roleCache = CacheFactory.getCache(CacheFactory.ROLE_PERMISSION_CACHE);
        Subject user = SecurityUtils.getSubject();
        if(user.isAuthenticated()){
            List<Role> roles = roleService.getUserRoles(getCurUser().getUser().getUserId());
            for (int i = 0; i < roles.size(); i++) {
                roleCache.remove(roles.get(i).getRoleName());
            }
            roleCache.remove(ShiroRoles.SUPERMAN);
            roleCache.remove(ShiroRoles.SYSMAN);
            roleCache.remove(ShiroRoles.GUEST);
            roleCache.remove(ShiroRoles.USER);
            roleCache.remove(ShiroRoles.ANONYMOUS);
            roleCache.remove(userName);
        }
    }

    @Override
    public Page<UserVO> pagelist(UserQuery query) {
        IPage<User> pageList = this.page(query.getPage(),query.getQueryWrapper());

        //最终的结果集  userVo 去除敏感信息
        List<UserVO> result = Lists.newArrayList();
        Page<UserVO> resultList = new Page<>();
        BeanUtils.copyProperties(pageList,resultList);
        resultList.setRecords(result);

        for (int i = 0; i <pageList.getRecords().size() ; i++) {
            UserVO user = new UserVO();
            BeanUtils.copyProperties(pageList.getRecords().get(i),user);
            result.add(user);
        }
        return resultList;
    }

    @Override
    public IPage<UserVO> findUserGroupRole(UserQuery query) {
        IPage<UserVO> resultList = this.mapper.findUserGroupRole(query.getPage(),query.getQueryWrapper());
        return resultList;
    }

    @Override
    public void delete(Long id) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("USER_ID",id);

        //删除用户组关联关系
        userGroupMapper.delete(qw);
        userRoleMapper.delete(qw);
        this.mapper.deleteById(id);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.24 17:05
     * @Description: 退出系统
     **/
    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();

        //清除缓存
        if(subject.getPrincipal()!=null){
            clearCache(subject.getPrincipal().toString());
        }
        String token = LivContextUtils.getRequestToken(LivContextUtils.getRequest());
        //退出的话还是用 response中的token，request中的有可能已经被更新，与缓存中不一致
        CacheFactory.getLoginSuccessSubjectCache().remove(token);
        CacheFactory.getLoginSuccessSubjectCache().remove(JwtUtil.getJwtIdByToken(token));
        //这特么有毛用
        subject.logout();
    }

    @Override
    public UserDO getCurUser() {
        Subject user = SecurityUtils.getSubject();
        //这里判断用户是否已登录// 判断是否有ANONYMOUS角色，判断角色的动作可以初始化缓存，使得后续可以获取到用户信息
        if(user.isAuthenticated()&&user.hasRole(ShiroRoles.ANONYMOUS)){
            return (UserDO)CacheFactory.getCache(CacheFactory.SHIRO_AUTHORIZATIONCACHENAME).get(user.getPrincipals());
        }
        return null;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.19 17:05
     * @Description: 用户注册
     **/
    public boolean reg(User user){
        user.setCreateDate(new Date());
        PasswordHelper.encryptNewUserPassword(user);
        mapper.insert(user);

        //新增用户组关联关系
        Long[] groupIds = user.getGroupIds();
        if(groupIds!=null){
            for (int i = 0; i <groupIds.length ; i++) {
                UserGroup ug = new UserGroup();
                ug.setGroupId(groupIds[i]);
                ug.setUserId(user.getUserId());
                userGroupMapper.insert(ug);
            }
        }
        //新增角色关联关系
        Long[] roleIds= user.getRoleIds();
        if(roleIds!=null){
            for (int i = 0; i <roleIds.length ; i++) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleIds[i]);
                ur.setUserId(user.getUserId());
                userRoleMapper.insert(ur);
            }
        }
        return true;
    }


    @Override
    public void doupdate(User user) {
        User databaseUser = this.getById(user.getUserId());
        user.setPassword(databaseUser.getPassword());

        QueryWrapper qw = new QueryWrapper();
        qw.eq("USER_ID",user.getUserId());

        //删除用户组关联关系
        userGroupMapper.delete(qw);
        //新增用户组关联关系
        Long[] groupIds = user.getGroupIds();
        if(groupIds!=null){
            for (int i = 0; i <groupIds.length ; i++) {
                UserGroup ug = new UserGroup();
                ug.setGroupId(groupIds[i]);
                ug.setUserId(user.getUserId());
                userGroupMapper.insert(ug);
            }
        }

        //删除用户角色关系
        userRoleMapper.delete(qw);
        //新增角色关联关系
        Long[] roleIds= user.getRoleIds();
        if(roleIds!=null){
            for (int i = 0; i <roleIds.length ; i++) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleIds[i]);
                ur.setUserId(user.getUserId());
                userRoleMapper.insert(ur);
            }
        }
    }
    /**
     * @Author: LiV
     * @Date: 2020.4.19 20:44
     * @Description: 根据用户名查询用户
     **/
    @Override
    public User findByUserName(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name",username);
        List<User> user = mapper.selectList(qw);
        if(user != null&&user.size()>0) {
            return user.get(0);
        }
        return null;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.20 14:22
     * @Description: 解锁用户
     **/
    @Override
    public void unlock(String username) {
        RetryLimitHashedCredentialsMatcher matcher = (RetryLimitHashedCredentialsMatcher)LivContextUtils.getBean("credentialsMatcher");
        matcher.unlockAccount(username);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.20 14:22
     * @Description: 解锁用户
     **/
    @Override
    public void lock(String username) {
        RetryLimitHashedCredentialsMatcher matcher = (RetryLimitHashedCredentialsMatcher)LivContextUtils.getBean("credentialsMatcher");
        matcher.lockAccount(username);
    }




}
