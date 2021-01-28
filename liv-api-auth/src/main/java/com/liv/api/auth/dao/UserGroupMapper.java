package com.liv.api.auth.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.dao.datamodel.UserGroup;
import com.liv.api.auth.domainmodel.PermissionDO;
import com.liv.api.auth.viewmodel.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("apiUserGroupMapper")
public interface UserGroupMapper extends BaseMapper<UserGroup> {

}