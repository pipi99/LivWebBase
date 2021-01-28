package com.liv.auth.controller;

import com.liv.api.auth.dao.ResourceMapper;
import com.liv.api.auth.dao.datamodel.Resource;
import com.liv.api.auth.service.ResourceService;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.ResultBody;
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
 * @Description: 资源管理控制器
 **/
@RestController

@RequestMapping(value = "/auth/resource")
@Api(tags = "资源管理")
public class ResourceController extends BaseController<ResourceMapper, Resource, ResourceService> {
    @ApiOperation(value = "根据ID查询资源", notes="根据给定的资源ID 查询资源")
    @ApiImplicitParam(name = "ResourceId", value = "当前登录资源ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{ResourceId}")
    public ResultBody getById(@PathVariable("ResourceId") Long ResourceId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(ResourceId));
    }

//    @ApiOperation(value = "分页查询资源列表", notes="分页查询资源列表")
//    @PostMapping(value="/pagelist")
//    public DataBody pagelist(@RequestBody ResourceQuery query) throws Exception {
//        QueryWrapper<Resource> qw = query.getQueryWrapper();
//        IPage<ResourceDO>  pageList = service.findPageList(query.getPage(),qw);
//        return DataBody.success(pageList);
//    }

//    @ApiOperation(value = "查询资源列表", notes="查询资源列表")
//    @PostMapping(value="/list")
//    public DataBody list(@RequestBody Resource Resource) throws Exception {
//        return DataBody.success(service.getTreeList(Resource));
//    }
//
//    @ApiOperation(value = "分页查询资源列表", notes="分页查询资源列表")
//    @GetMapping(value="/queryByParentId")
//    public DataBody queryByParentId(Long parentId) throws Exception {
//        return DataBody.success(service.findByParentId(parentId));
//    }

    @ApiOperation(value = "新增资源", notes="新增资源")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Resource d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新资源", notes="资源更新资源,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Resource d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }
//
//    @ApiOperation(value = "删除资源", notes="删除资源,根据主键id删除")
//    @DeleteMapping(value="/remove/{id}")
//    public ResultBody delete(@PathVariable("id") Long id) throws Exception {
//        List<ResourceDO> list = service.findByParentId(id);
//        if(list!=null&&list.size()>0){
//            return ResultBody.error("删除失败，请先删除下级资源");
//        }
//        boolean flag = service.removeById(id);
//        return ResultBody.success(flag?"删除成功":"删除失败，未删除任何数据");
//    }

    @ApiOperation(value = "批量删除资源", notes="删除资源,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
