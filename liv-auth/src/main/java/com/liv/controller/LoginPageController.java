//package com.liv.controller;
//
//import com.liv.dao.UserMapper;
//import com.liv.dao.datamodel.User;
//import com.liv.service.UserService;
//import com.liv.web.base.BaseController;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.DisabledAccountException;
//import org.apache.shiro.authc.IncorrectCredentialsException;
//import org.apache.shiro.authc.UnknownAccountException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author LiV
// * @Title:
// * @Package com.liv.controller
// * @Description: 用户登录管理
// * @date 2020.4.19  17:32
// * @email 453826286@qq.com
// */
//@Controller
//@Api(tags = "用户登录管理")
//public class LoginPageController extends BaseController<UserMapper, User, UserService> {
////    @ApiOperation(value = "查询用户", notes="根据给定的用户ID 查询用户")
////    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
////    @GetMapping(value="/login")
////    public User login(@RequestBody User user) throws Exception {
////        return service().getById(user.getOrgId());
////    }
////
////    /**
////     * 解除admin 用户的限制登录
////     * 写死的 方便测试
////     * @return
////     */
////    @RequestMapping("/unlockAccount")
////    public ResultBody unlockAccount(){
////
////        retryLimitHashedCredentialsMatcher.unlockAccount("admin");
////
////        return ResultBody.success("用户解锁成功");
////    }
//
//    /**
//     * get请求，登录页面跳转
//     * @return
//     */
//    @ApiOperation(value = "用户登录", notes="用户登录")
//    @GetMapping("/login")
//    public String login() {
//        //如果已经认证通过，直接跳转到首页
//        if (SecurityUtils.getSubject().isAuthenticated()) {
//            return "redirect:/index";
//        }
//        return "login";
//    }
//
//
//
////    /**
////     * 退出
////     * @return
////     */
////    @ApiOperation(value = "用户退出", notes="用户退出")
////    @RequestMapping("/logout")
////    public String logout() {
////        SecurityUtils.getSubject().logout();
////        return "login";
////    }
//}
