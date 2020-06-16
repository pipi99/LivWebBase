package com.liv.api.base.dicttype;

import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
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
 * @Description: 字典类型管理控制器
 **/
@RestController

@RequestMapping(value = "/api/dicttype")
@Api(tags = "字典类型管理")
public class DictTypeController extends BaseController<DictTypeMapper, DictType, DictTypeService> {
    @ApiOperation(value = "根据ID查询字典类型", notes="根据给定的字典类型ID 查询字典类型")
    @ApiImplicitParam(name = "ictTypeId", value = "当前登录字典类型ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{DictTypeId}")
    public ResultBody getById(@PathVariable("DictTypeId") Long DictTypeId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(DictTypeId));
    }

    @ApiOperation(value = "查询字典类型列表", notes="查询字典类型列表")
    @GetMapping(value="/list")
    public DataBody list() throws Exception {
        return DataBody.success(service.list());
    }

    @ApiOperation(value = "分页查询字典类型列表", notes="分页查询字典类型列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody DictTypeQuery query) throws Exception {
        return DataBody.success(service.pagelist(query));
    }

    @ApiOperation(value = "新增字典类型", notes="新增字典类型")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid DictType d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新字典类型", notes="字典类型更新字典类型,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid DictType d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除字典类型", notes="删除字典类型,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        service.removeById(id);
        return ResultBody.success("删除成功");
    }

    @ApiOperation(value = "批量删除字典类型", notes="删除字典类型,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
