package com.liv.api.auth.controller;

import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.base.annotation.AvoidDuplicateSubmission;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 用户管理控制器
 **/
@RestController("api_auth_UserController")

@RequestMapping(value = "/p/api/user")
@Api(tags = "AUTH_用户管理")
@Slf4j
public class UserController extends BaseController<UserMapper, User, UserService> {
    @ApiOperation(value = "根据ID查询用户", notes="根据给定的用户ID 查询用户")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/p/getById/{userId}")
    public ResultBody getById(@PathVariable("userId") Long userId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(userId));
    }

    @ApiOperation(value = "查询用户列表", notes="查询用户列表")
    @GetMapping(value="/list")
    public ResultBody list() throws Exception {
        return ResultBody.success(service.list());
    }

    @ApiOperation(value = "注册用户", notes="注册用户")
    @PostMapping(value="/reg")
    @ValidResult
    public ResultBody reg(@RequestBody(required = true) @Valid User d, BindingResult result) {
        return ResultBody.success(service.reg(d));
    }

    @ApiOperation(value = "新增用户", notes="新增用户")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid User d, BindingResult result) throws InterruptedException {
        return ResultBody.success(service.save(d));
    }

    @ApiOperation(value = "获取当前登录用户", notes="获取当前登录用户")
    @GetMapping(value="/getCurrUser")
    public ResultBody getCurrUser() {
        return ResultBody.success(service.getCurUser().getUser());
    }
//
//    @ApiOperation(value = "更新用户", notes="用户更新用户,根据主键id更新")
//    @PutMapping(value="/update")
//    public ResultBody update(@RequestBody(required = true) User d) {
//        return ResultBody.success(service.updateById(d));
//    }
//
//    @ApiOperation(value = "设置为默认桌面", notes="设置为默认桌面,根据主键id更新")
//    @PutMapping(value="/setDefault/{id}")
//    public ResultBody setDefault(@PathVariable("id") Long id) {
//        return ResultBody.success(service.setDefault(id));
//    }
//
//    @ApiOperation(value = "逻辑删除用户", notes="逻辑删除用户,根据主键id删除")
//    @DeleteMapping(value="/remove/{id}")
//    public ResultBody delete(@PathVariable("id") Long id){
//        return ResultBody.success(service.removeById(id));
//    }

}
