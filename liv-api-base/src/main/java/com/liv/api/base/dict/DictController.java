package com.liv.api.base.dict;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liv.api.base.annotation.ValidResult;
import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.api.base.base.ResultBody;
import com.liv.api.base.utils.LivCollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 字典管理控制器
 **/
@RestController
@RequestMapping(value = "/api/dict")
@Api(tags = "字典管理")
public class DictController extends BaseController<DictMapper, Dict, DictService> {
    @ApiOperation(value = "根据ID查询字典", notes="根据给定的字典ID 查询字典")
    @ApiImplicitParam(name = "DictId", value = "当前登录字典ID", required = true, dataType = "String", paramType = "path",defaultValue = "1")
    @GetMapping(value="/getById/{DictId}")
    public ResultBody getById(@PathVariable("DictId") Long DictId) throws Exception {
        SecurityUtils.getSubject().checkRole("admin");
        return ResultBody.success(service.getById(DictId));
    }

    @ApiOperation(value = "查询字典列表", notes="查询字典列表")
    @RequestMapping(value="/cascadelist/{dictType}")
    public DataBody cascadelist(@PathVariable("dictType") String dictType) throws Exception {
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(dictType)){
            qw.eq("D_TYPE",dictType);
        }
        List<Dict> dicts = service.list(qw);

        //复制属性
        List<DictVO> vos = dicts.stream().map(dict -> {
            DictVO vo = new DictVO();
            BeanUtils.copyProperties(dict,vo);
            return vo;
        }).collect(Collectors.toList());

        //返回树结构
        return DataBody.success(LivCollectionUtils.getTree(vos,"dCode","parentCode","children"));
    }

    @ApiOperation(value = "查询字典列表", notes="查询字典列表")
    @RequestMapping(value="/list/{dictType}")
    public DataBody list(@PathVariable String dictType) throws Exception {


        QueryWrapper<Dict> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(dictType)&&!"all_dict_all_dict_all".equalsIgnoreCase(dictType)){
            qw.eq("D_TYPE",dictType);
        }
        List<Dict> dicts = service.list(qw);

        //复制属性
        List<DictVO> vos = dicts.stream().map(dict -> {
            DictVO vo = new DictVO();
            BeanUtils.copyProperties(dict,vo);
            return vo;
        }).collect(Collectors.toList());

        return DataBody.success(vos);
    }

    @ApiOperation(value = "分页查询字典列表", notes="分页查询字典列表")
    @PostMapping(value="/pagelist")
    public DataBody pagelist(@RequestBody DictQuery query) throws Exception {
        IPage<Dict> pageList = service.page(query.getPage(),query.getQueryWrapper());
        return DataBody.success(pageList);
    }

    @ApiOperation(value = "新增字典", notes="新增字典")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Dict d, BindingResult result) {
        service.save(d);
        return ResultBody.success("保存成功");
    }

    @ApiOperation(value = "更新字典", notes="字典更新字典,根据主键id更新")
    @PutMapping(value="/update")
    @ValidResult
    public ResultBody update(@RequestBody(required = true) @Valid Dict d, BindingResult result) {
        service.updateById(d);
        return ResultBody.success("修改成功");
    }

    @ApiOperation(value = "删除字典", notes="删除字典,根据主键id删除")
    @DeleteMapping(value="/remove/{id}")
    public ResultBody delete(@PathVariable("id") Long id){
        service.removeById(id);
        return ResultBody.success("删除成功");
    }

    @ApiOperation(value = "批量删除字典", notes="删除字典,根据主键id删除")
    @PostMapping(value="/remove")
    public ResultBody delete(@RequestBody List<Long> ids){
        service.removeByIds(ids);
        return ResultBody.success("删除成功");
    }

}
