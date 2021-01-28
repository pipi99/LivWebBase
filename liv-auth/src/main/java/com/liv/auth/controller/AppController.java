package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.service.MenuService;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.auth.dao.AppMapper;
import com.liv.auth.dao.datamodel.App;
import com.liv.auth.dao.datamodel.AppQuery;
import com.liv.auth.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 应用管理控制器
 **/
@RestController
@RequestMapping(value = "/public/auth/app")
@Api(tags = "应用管理")
public class AppController extends BaseController<AppMapper, App, AppService> {


    private MenuService menuService;
    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "根据ID查询应用", notes="根据给定的应用ID 查询应用")
    @ApiImplicitParam(name = "appId", value = "当前登录应用ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{appId}")
    public ResultBody getById(@PathVariable("appId") Long AppId) throws Exception {
        return ResultBody.success(service.getById(AppId));
    }

    @ApiOperation(value = "查询应用列表", notes="查询应用列表")
    @GetMapping(value="/list")
    public DataBody list() throws Exception {
        return DataBody.success(service.list());
    }

    @ApiOperation(value = "分页查询应用列表", notes="分页查询应用列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody AppQuery query) throws Exception {
        IPage<App> pageList = service.page(query.getPage(),query.getQueryWrapper());
        return DataBody.success(pageList);
    }

    @ApiOperation(value = "新增应用", notes="新增应用")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid App d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新应用", notes="应用更新应用,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid App d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除应用", notes="删除应用,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.eq("APP_ID",id);
        List list = menuService.list(qw);
        if(list!=null&&list.size()>0){
            return ResultBody.error("应用下有菜单，删除失败");
        }
        boolean result = service.removeById(id);
        return ResultBody.success(result?"删除成功":"删除失败");
    }

    @ApiOperation(value = "批量删除应用", notes="删除应用,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
