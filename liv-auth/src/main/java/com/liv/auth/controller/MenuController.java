package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.auth.dao.MenuMapper;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.dao.datamodel.MenuQuery;
import com.liv.api.auth.domainmodel.MenuDO;
import com.liv.api.auth.service.MenuService;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.auth.dao.datamodel.App;
import com.liv.auth.dao.datamodel.AppQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.ListUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 菜单管理控制器
 **/
@RestController

@RequestMapping(value = "/auth/menu")
@Api(tags = "菜单管理")
public class MenuController extends BaseController<MenuMapper, Menu, MenuService> {
    @ApiOperation(value = "根据ID查询菜单", notes="根据给定的菜单ID 查询菜单")
    @ApiImplicitParam(name = "MenuId", value = "当前登录菜单ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{MenuId}")
    public ResultBody getById(@PathVariable("MenuId") Long MenuId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(MenuId));
    }

//    @ApiOperation(value = "分页查询菜单列表", notes="分页查询菜单列表")
//    @PostMapping(value="/pagelist")
//    public DataBody pagelist(@RequestBody MenuQuery query) throws Exception {
//        QueryWrapper<Menu> qw = query.getQueryWrapper();
//        IPage<MenuDO>  pageList = service.findPageList(query.getPage(),qw);
//        return DataBody.success(pageList);
//    }

    @ApiOperation(value = "查询菜单列表", notes="查询菜单列表")
    @PostMapping(value="/list")
    public DataBody list(@RequestBody Menu menu) throws Exception {
        return DataBody.success(service.getTreeList(menu));
    }

    @ApiOperation(value = "分页查询菜单列表", notes="分页查询菜单列表")
    @GetMapping(value="/queryByParentId")
    public DataBody queryByParentId(Long parentId) throws Exception {
        return DataBody.success(service.findByParentId(parentId));
    }

    @ApiOperation(value = "新增菜单", notes="新增菜单")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Menu d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新菜单", notes="菜单更新菜单,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Menu d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除菜单", notes="删除菜单,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id) throws Exception {
        List<MenuDO> list = service.findByParentId(id);
        if(list!=null&&list.size()>0){
            return ResultBody.error("删除失败，请先删除下级菜单");
        }
        boolean flag = service.removeById(id);
        return ResultBody.success(flag?"删除成功":"删除失败，未删除任何数据");
    }

    @ApiOperation(value = "批量删除菜单", notes="删除菜单,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
