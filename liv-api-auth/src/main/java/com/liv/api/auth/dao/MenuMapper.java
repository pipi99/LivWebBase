package com.liv.api.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.domainmodel.PermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("apiMenuMapper")
public interface MenuMapper extends BaseMapper<Menu> {

}