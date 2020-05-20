package com.liv.api.auth.controller;

import com.liv.api.auth.dao.datamodel.Menus;
import com.liv.api.auth.service.MenusService;
import com.liv.api.auth.utils.UserUtils;
import com.liv.api.auth.web.api.base.base.BaseController;
import com.liv.api.auth.dao.MenusMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 菜单，应用管理控制器
 **/
@RestController
@RequestMapping(value = "menus")
@Api(tags = "用户菜单，应用")
public class MenusController extends BaseController<MenusMapper, Menus, MenusService> {

    @ApiOperation(value = "查询用户菜单，应用", notes="根据给定的用户ID 查询用户菜单，应用")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
    @GetMapping(value="/{userId}")
    public List<Menus> list(@PathVariable("userId") Long userId) throws Exception {
        return service.findByUserId(userId);
    }

    @ApiOperation(value = "查询当前用户菜单，应用", notes="查询当前用户菜单，应用")
    @GetMapping(value="/getCurrUser")
    public List<Menus> list() throws Exception {
        Long userId = UserUtils.getCurrentUesr().getUser().getUserId();
        return service.findByUserId(userId);
    }
}
