package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.service.UserService;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.api.base.base.BaseController;
import com.liv.auth.dao.OrganMapper;
import com.liv.auth.dao.datamodel.Organ;
import com.liv.auth.dao.datamodel.OrganQuery;
import com.liv.auth.service.OrganService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 组织机构管理控制器
 **/
@RestController

@RequestMapping(value = "/auth/organ")
@Api(tags = "组织机构管理")
public class OrganController extends BaseController<OrganMapper, Organ, OrganService> {


    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "根据ID查询组织机构", notes="根据给定的组织机构ID 查询组织机构")
    @ApiImplicitParam(name = "OrganId", value = "当前登录组织机构ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{OrganId}")
    public ResultBody getById(@PathVariable("OrganId") Long OrganId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(OrganId));
    }

    @ApiOperation(value = "查询组织机构列表", notes="查询组织机构列表")
    @GetMapping(value="/list")
    public List<Organ> list() throws Exception {
        return service.list();
    }

    @ApiOperation(value = "查询组织机构列表", notes="查询组织机构列表")
    @GetMapping(value="/treelist")
    public DataBody treelist() throws Exception {
        return DataBody.success(service.treelist());
    }

    @ApiOperation(value = "分页查询组织机构列表", notes="分页查询组织机构列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody OrganQuery query) throws Exception {
        IPage<Organ> pageList = service.page(query.getPage(),query.getQueryWrapper());
        return DataBody.success(pageList);
    }

    @ApiOperation(value = "新增组织机构", notes="新增组织机构")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Organ d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新组织机构", notes="组织机构更新组织机构,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Organ d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除组织机构", notes="删除组织机构,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){

        QueryWrapper<Organ> qw = new QueryWrapper<>();
        qw.eq("PARENT_ID",id);
        List list = service.list(qw);

        if(list!=null&&list.size()>0){
            return ResultBody.error("有下级机构，删除失败！");
        }

        QueryWrapper<User> qwu = new QueryWrapper<>();
        qwu.eq("ORG_ID",id);
        List userlist = userService.list(qwu);
        if(userlist!=null&&userlist.size()>0){
            return ResultBody.error("机构下有用户，删除失败！");
        }

        boolean flag = service.removeById(id);
        return ResultBody.success(flag?"删除成功":"删除失败");
    }

    @ApiOperation(value = "批量删除组织机构", notes="删除组织机构,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
