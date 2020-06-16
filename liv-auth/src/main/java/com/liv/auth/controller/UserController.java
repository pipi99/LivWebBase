package com.liv.auth.controller;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.domainmodel.UserQuery;
import com.liv.api.auth.service.UserService;
import com.liv.api.auth.viewmodel.UserVO;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.api.base.utils.LivPropertiesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    LivPropertiesUtils livPropertiesUtils;

    @ApiOperation(value = "根据ID查询用户", notes="根据给定的用户ID 查询用户")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{userId}")
    public ResultBody getById(@PathVariable("userId") Long userId) throws Exception {
        UserVO user = new UserVO();
        BeanUtils.copyProperties(service.getById(userId),user);
        return ResultBody.success(user);
    }

    @ApiOperation(value = "分页查询用户列表", notes="分页查询用户列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody UserQuery query) throws Exception {
        return DataBody.success(service.pagelist(query));
    }

    @ApiOperation(value = "新增用户", notes="新增用户")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid User u, BindingResult result) {
        u.setPassword(livPropertiesUtils.get(LivPropertiesUtils.DEFAULT_PASSWORD));
        return ResultBody.success(service.reg(u));
    }

    @ApiOperation(value = "更新用户", notes="用户更新用户,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true)@Valid User u, BindingResult result) throws Exception {
        User databaseUser = this.service.getById(u.getUserId());
        u.setPassword(databaseUser.getPassword());
        return ResultBody.success(service.updateById(u));
    }


    @ApiOperation(value = "删除用户", notes="删除用户,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        return ResultBody.success(service.removeById(id));
    }

}
