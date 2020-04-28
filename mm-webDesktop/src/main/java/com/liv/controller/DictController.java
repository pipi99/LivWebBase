package com.liv.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.DictMapper;
import com.liv.dao.datamodel.Dict;
import com.liv.service.DictService;
import com.liv.web.api.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 字典管理控制器
 **/
@RestController
@RequestMapping(value = "dict")
@Api(tags = "数据字典")
public class DictController extends BaseController<DictMapper,Dict, DictService> {

    @ApiOperation(value = "查询字典", notes="根据给定的字典类型查询数据字典")
    @ApiImplicitParam(name = "dicttype", value = "可选值，如下： <br/>背景图片：desktopbgimg <br/>主题：desktopsubject <br/>分辨率：desktopresolution", required = true, dataType = "String", paramType = "path",defaultValue = "desktopresolution")
    @GetMapping(value="/{dicttype}")
    public List<Dict> list(@PathVariable("dicttype") String dicttype) throws Exception {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        qw.eq("dicttype",dicttype);
        return service().list(qw);
    }
}
