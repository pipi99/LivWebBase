package com.liv.controller;

import com.liv.api.auth.utils.ApiAuthUtils;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.ResultBody;
import com.liv.dao.DesktopMapper;
import com.liv.dao.datamodel.Desktop;
import com.liv.domainmodel.DesktopDO;
import com.liv.service.DesktopService;
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
@RequestMapping(value = "desktop")
@Api(tags = "用户桌面")
public class DesktopController extends BaseController<DesktopMapper, Desktop, DesktopService> {

    @ApiOperation(value = "查询用户桌面", notes="根据给定的用户ID 查询用户桌面")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
    @GetMapping(value="/{userId}")
    public List<DesktopDO> list(@PathVariable("userId") Long userId) throws Exception {
        return service.findByUserId(userId);
    }

    @ApiOperation(value = "查询当前用户桌面", notes="查询当前用户桌面")
    @GetMapping(value="/currUser")
    public List<DesktopDO> listCurrUser() throws Exception {
        Long userId =  ApiAuthUtils.getCurrentUesr().getUser().getUserId();
        return service.findByUserId(userId);
    }

    @ApiOperation(value = "保存用户桌面", notes="用户初始登录没有用户桌面，默认新增一个")
    @PostMapping(value="/save")
    public ResultBody save(@RequestBody(required = true) @Valid Desktop d, BindingResult result) {
        validateResult(result);
        return ResultBody.success(service.save(d));
    }

    @ApiOperation(value = "更新用户桌面", notes="用户更新用户桌面,根据主键id更新")
    @PutMapping(value="/update")
    public ResultBody update(@RequestBody(required = true) Desktop d) {
        return ResultBody.success(service.updateById(d));
    }

    @ApiOperation(value = "设置为默认桌面", notes="设置为默认桌面,根据主键id更新")
    @PutMapping(value="/setDefault/{id}")
    public ResultBody setDefault(@PathVariable("id") Long id) {
        return ResultBody.success(service.setDefault(id));
    }

    @ApiOperation(value = "逻辑删除用户桌面", notes="逻辑删除用户桌面,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        return ResultBody.success(service.removeById(id));
    }
}
