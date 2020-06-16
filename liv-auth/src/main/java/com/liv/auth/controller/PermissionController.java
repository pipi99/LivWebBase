package com.liv.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.auth.service.MenuService;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.ResultBody;
import com.liv.auth.dao.OrganMapper;
import com.liv.auth.dao.datamodel.Organ;
import com.liv.auth.service.OrganService;
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
 * @Description: 组织机构管理控制器
 **/
@RestController

@RequestMapping(value = "/auth/perm")
@Api(tags = "权限管理")
public class PermissionController  {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "更新菜单权限过滤规则", notes="更新菜单权限过滤规则")
    @GetMapping(value="/refreshPerm/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        menuService.setPermissionFilter();
        return ResultBody.success("操作成功");
    }

}
