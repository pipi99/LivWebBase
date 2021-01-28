package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.GroupsQuery;
import com.liv.api.auth.service.MenuService;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.auth.dao.GroupMapper;
import com.liv.auth.service.GroupService;
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
 * @Description: 用户组管理控制器
 **/
@RestController

@RequestMapping(value = "/auth/group")
@Api(tags = "用户组管理")
public class GroupController extends BaseController<GroupMapper, Groups, GroupService> {

    private MenuService menuService;
    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "根据ID查询用户组", notes="根据给定的用户组ID 查询用户组")
    @ApiImplicitParam(name = "groupId", value = "当前用户组ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{groupId}")
    public ResultBody getById(@PathVariable("groupId") Long groupId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(groupId));
    }

    @ApiOperation(value = "查询用户组列表", notes="查询用户组列表")
    @GetMapping(value="/list")
    public DataBody list() throws Exception {
        return DataBody.success(service.list());
    }

    @ApiOperation(value = "分页查询用户组列表", notes="分页查询用户组列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody GroupsQuery query) throws Exception {
        IPage<Groups> pageList = service.findGroupRole(query);
        return DataBody.success(pageList);
    }

    @ApiOperation(value = "新增用户组", notes="新增用户组")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Groups d, BindingResult result) {
        service.add(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新用户组", notes="用户组更新用户组,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Groups d, BindingResult result) {
        service.update(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除用户组", notes="删除用户组,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResultBody.success("删除成功");
    }

    @ApiOperation(value = "批量删除用户组", notes="删除用户组,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
