package com.liv.sp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.api.base.utils.CacheUtils;
import com.liv.sp.dao.SpMapper;
import com.liv.sp.dao.datamodel.Sp;
import com.liv.sp.dao.datamodel.SpQuery;
import com.liv.sp.service.SpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.controller
 * @Description: 控制器
 * @date 2020.12.11  10:51
 * @email 453826286@qq.com
 */
@RestController
@RequestMapping(value = "/test")
@Api(tags = "测试控制器")
public class SpController  extends BaseController<SpMapper, Sp, SpService> {

    @Autowired
    private CacheUtils cacheUtils;

    @ApiOperation(value = "根据ID查询", notes="根据给定的ID 查询")
    @ApiImplicitParam(name = "id", value = "当前登录ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{id}")
    public ResultBody getById(@PathVariable("id") Long SpId) throws Exception {
        return ResultBody.success(service.getById(SpId));
    }

    @ApiOperation(value = "查询列表", notes="查询列表")
    @GetMapping(value="/list")
    public DataBody list() throws Exception {
        Object obj = cacheUtils.get("list");
        if(obj==null){
            obj = service.list();
            cacheUtils.put("list",obj);
        }

        return DataBody.success(obj);
//        return DataBody.success(service.list());
    }

    @ApiOperation(value = "分页查询列表", notes="分页查询列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody SpQuery query) throws Exception {
        IPage<Sp> pageList = service.page(query.getPage(),query.getQueryWrapper());
        return DataBody.success(pageList);
    }

    @ApiOperation(value = "新增", notes="新增")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Sp d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新", notes="更新,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Sp d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除", notes="删除,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        boolean result = service.removeById(id);
        return ResultBody.success(result?"删除成功":"删除失败");
    }

    @ApiOperation(value = "批量删除", notes="删除,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }
}
