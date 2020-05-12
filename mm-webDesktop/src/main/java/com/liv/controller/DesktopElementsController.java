package com.liv.controller;

import com.liv.dao.DesktopElementsMapper;
import com.liv.dao.datamodel.DesktopElements;
import com.liv.service.DesktopElementsService;
import com.liv.web.api.base.base.BaseController;
import com.liv.web.api.base.base.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 桌面管理控制器
 **/
@RestController
@RequestMapping(value = "desktopelements")
@Api(tags = "用户桌面元素元素")
public class DesktopElementsController extends BaseController<DesktopElementsMapper, DesktopElements, DesktopElementsService> {

//    @ApiOperation(value = "查询用户桌面元素", notes="根据给定的用户ID 查询用户桌面元素")
//    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
//    @GetMapping(value="/{userId}")
//    public List<DesktopElements> list(@PathVariable("userId") Long userId) {
//        QueryWrapper<DesktopElements> qw = new QueryWrapper<>();
//        qw.eq("USER_ID",userId);
//        return service.list(qw);
//    }

    @ApiOperation(value = "保存用户桌面元素", notes="用户初始登录没有用户桌面元素，默认新增一个")
    @PostMapping(value="/save")
    public ResultBody save(@RequestBody(required = true) @Valid DesktopElements d, BindingResult result) {
        validateResult(result);
        return ResultBody.success(service.save(d));
    }

    @ApiOperation(value = "更新用户桌面元素", notes="用户更新用户桌面元素,根据主键id更新")
    @PutMapping(value="/update")
    public ResultBody update(@RequestBody(required = true) DesktopElements d) {
        return ResultBody.success(service.updateById(d));
    }

    @ApiOperation(value = "删除用户桌面元素", notes="用户更新用户桌面元素,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id) {
        return ResultBody.success(service.removeById(id));
    }
}
