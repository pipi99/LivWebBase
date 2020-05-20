package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.Dict;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictMapper  extends BaseMapper<Dict> {

}