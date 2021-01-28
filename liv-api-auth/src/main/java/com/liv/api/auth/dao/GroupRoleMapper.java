package com.liv.api.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.api.auth.dao.datamodel.GroupRole;
import com.liv.api.auth.dao.datamodel.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("apiGroupRoleMapper")
public interface GroupRoleMapper extends BaseMapper<GroupRole> {

}