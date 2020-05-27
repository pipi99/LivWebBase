package com.liv.api.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.auth.dao.DesktopSubjectMapper;
import com.liv.api.auth.dao.datamodel.DesktopSubject;
import com.liv.api.auth.service.DesktopSubjectService;
import com.liv.api.auth.utils.ApiAuthUtils;
import com.liv.api.auth.web.api.base.base.BaseController;
import com.liv.api.auth.web.api.base.base.ResultBody;
import com.liv.api.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 桌面主题管理控制器
 **/
@RestController
@RequestMapping(value = "desktopsubject")
@Api(tags = "桌面主题")
public class DesktopSubjectController extends BaseController<DesktopSubjectMapper, DesktopSubject, DesktopSubjectService> {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询桌面主题", notes="根据给定的用户ID 查询桌面主题")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
    @GetMapping(value="/{userId}")
    public List<DesktopSubject> find(@PathVariable("userId") Long userId) {
        QueryWrapper<DesktopSubject> qw = new QueryWrapper<>();
        qw.eq("USER_ID",userId);
        return service.list(qw);
    }

    @ApiOperation(value = "查询当前用户桌面主题", notes="查询当前登录用户的桌面主题")
    @GetMapping(value="/currUser")
    public List<DesktopSubject> findCurrUser() {
        Long userId =  ApiAuthUtils.getCurrentUesr().getUser().getUserId();
        QueryWrapper<DesktopSubject> qw = new QueryWrapper<>();
        qw.eq("USER_ID",userId);
        return service.list(qw);
    }

    @ApiOperation(value = "保存桌面主题", notes="用户初始登录没有桌面主题，默认新增一个")
    @PostMapping(value="/save")
    public ResultBody save(@RequestBody(required = true) DesktopSubject d) {
        return ResultBody.success(service.save(d));
    }

    @ApiOperation(value = "更新桌面主题", notes="用户更新桌面主题,根据主键id更新")
    @PutMapping(value="/update")
    public ResultBody update(@RequestBody(required = true) DesktopSubject d) {
        return ResultBody.success(service.updateById(d));
    }
}
