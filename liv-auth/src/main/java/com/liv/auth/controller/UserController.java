package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.auth.shiro.ShiroRoles;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.ResultBody;
import com.liv.auth.dao.UserMapper;
import com.liv.auth.dao.datamodel.Organ;
import com.liv.auth.dao.datamodel.User;
import com.liv.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 用户管理控制器
 **/
@RestController

@RequestMapping(value = "/auth/user")
@Api(tags = "用户管理")
public class UserController extends BaseController<UserMapper, User, UserService> {
    @ApiOperation(value = "根据ID查询用户", notes="根据给定的用户ID 查询用户")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{userId}")
    public ResultBody getById(@PathVariable("userId") Long userId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(userId));
    }

    @ApiOperation(value = "查询用户列表", notes="查询用户列表")
    @PostMapping(value="/pagelist")
    public IPage<User> pagelist(int pageIndex,int pageSize,@RequestBody User u) throws Exception {
        QueryWrapper<User> wrapper = new QueryWrapper(u);
        Page<User> page = new Page<>(1,2);
        IPage<User> pageList = service.page(page,wrapper);
        return pageList;
    }

    @ApiOperation(value = "查询用户列表", notes="查询用户列表")
    @GetMapping(value="/list")
    public ResultBody list() throws Exception {
        return ResultBody.success(service.list());
    }

    @ApiOperation(value = "新增用户", notes="新增用户")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid User d, BindingResult result) {
        return ResultBody.success(service.save(d));
    }

    @ApiOperation(value = "更新用户", notes="用户更新用户,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true)@Valid User d, BindingResult result) {
        return ResultBody.success(service.updateById(d));
    }


    @ApiOperation(value = "删除用户", notes="删除用户,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        return ResultBody.success(service.removeById(id));
    }

}
