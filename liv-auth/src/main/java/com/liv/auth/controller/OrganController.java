package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.ResultBody;
import com.liv.auth.dao.OrganMapper;
import com.liv.auth.dao.datamodel.Organ;
import com.liv.auth.dao.datamodel.User;
import com.liv.auth.service.OrganService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation(value = "分页查询组织机构列表", notes="分页查询组织机构列表")
    @PostMapping(value="/pagelist")
    public IPage<Organ> pagelist(int pageIndex,int pageSize,@RequestBody Organ o) throws Exception {
        QueryWrapper<Organ> wrapper = new QueryWrapper(o);
        Page<Organ> page = new Page<>(1,2);
        IPage<Organ> pageList = service.page(page,wrapper);
        return pageList;
    }

    @ApiOperation(value = "新增组织机构", notes="新增组织机构")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Organ d, BindingResult result) {
        return ResultBody.success(service.save(d));
    }

    @ApiOperation(value = "更新组织机构", notes="组织机构更新组织机构,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Organ d, BindingResult result) {
        return ResultBody.success(service.updateById(d));
    }

    @ApiOperation(value = "删除组织机构", notes="删除组织机构,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        return ResultBody.success(service.removeById(id));
    }

}
