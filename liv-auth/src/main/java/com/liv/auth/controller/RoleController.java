package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liv.api.auth.dao.RoleMapper;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.dao.datamodel.RoleQuery;
import com.liv.api.auth.service.MenuService;
import com.liv.api.auth.service.RoleService;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 角色管理控制器
 **/
@RestController

@RequestMapping(value = "/auth/role")
@Api(tags = "角色管理")
public class RoleController extends BaseController<RoleMapper, Role, RoleService> {


    private MenuService menuService;
    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "根据ID查询角色", notes="根据给定的角色ID 查询角色")
    @ApiImplicitParam(name = "roleId", value = "当前角色ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{roleId}")
    public ResultBody getById(@PathVariable("roleId") Long roleId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(roleId));
    }

    @ApiOperation(value = "查询角色列表", notes="查询角色列表")
    @GetMapping(value="/list")
    public DataBody list() throws Exception {
        return DataBody.success(service.list());
    }

    @ApiOperation(value = "分页查询角色列表", notes="分页查询角色列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody RoleQuery query) throws Exception {
        IPage<Role> pageList = service.page(query.getPage(),query.getQueryWrapper());
        return DataBody.success(pageList);
    }

    @ApiOperation(value = "新增角色", notes="新增角色")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Role d, BindingResult result) {
        d.setCreateDate(new Date());
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新角色", notes="角色更新角色,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Role d, BindingResult result) {
        d.setCreateDate(new Date());
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除角色", notes="删除角色,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        boolean result = service.removeById(id);
        return ResultBody.success(result?"删除成功":"删除失败");
    }

    @ApiOperation(value = "批量删除角色", notes="删除角色,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
