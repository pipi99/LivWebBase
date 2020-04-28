package com.liv.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.DesktopSubjectMapper;
import com.liv.dao.datamodel.DesktopSubject;
import com.liv.service.DesktopSubjectService;
import com.liv.web.api.base.BaseController;
import com.liv.web.api.base.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
public class DesktopSubjectController extends BaseController<DesktopSubjectMapper,DesktopSubject, DesktopSubjectService> {

    @ApiOperation(value = "查询桌面主题", notes="根据给定的用户ID 查询桌面主题")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
    @GetMapping(value="/{userId}")
    public List<DesktopSubject> find(@PathVariable("userId") Long userId) {
        QueryWrapper<DesktopSubject> qw = new QueryWrapper<>();
        qw.eq("USER_ID",userId);
        return service().list(qw);
    }

    @ApiOperation(value = "保存桌面主题", notes="用户初始登录没有桌面主题，默认新增一个")
    @PostMapping(value="/save")
    public ResultBody save(@RequestBody(required = true) DesktopSubject d) {
        return ResultBody.success(service().save(d));
    }

    @ApiOperation(value = "更新桌面主题", notes="用户更新桌面主题,根据主键id更新")
    @PutMapping(value="/update")
    public ResultBody update(@RequestBody(required = true) DesktopSubject d) {
        return ResultBody.success(service().updateById(d));
    }
}