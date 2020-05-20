package com.liv.api.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.auth.dao.datamodel.DesktopElements;
import com.liv.api.auth.web.api.base.base.BaseController;
import com.liv.api.auth.web.api.base.base.ResultBody;
import com.liv.api.auth.dao.DesktopElementsMapper;
import com.liv.api.auth.service.DesktopElementsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 桌面管理控制器
 **/
@RestController
@RequestMapping(value = "desktopelements")
@Api(tags = "桌面元素")
public class DesktopElementsController extends BaseController<DesktopElementsMapper, DesktopElements, DesktopElementsService> {

    @ApiOperation(value = "查询桌面元素", notes="根据给定的桌面ID 查询桌面元素")
    @ApiImplicitParam(name = "desktopId", value = "桌面ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
    @GetMapping(value="/{desktopId}")
    public List<DesktopElements> list(@PathVariable("desktopId") Long desktopId) {
        QueryWrapper<DesktopElements> qw = new QueryWrapper<>();
        qw.eq("DESKTOP_ID",desktopId);
        return service.list(qw);
    }

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
