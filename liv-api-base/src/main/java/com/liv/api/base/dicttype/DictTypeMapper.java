package com.liv.api.base.dicttype;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("apiDictTypeMapper")
public interface DictTypeMapper extends BaseMapper<DictType> {

}