package com.liv.web.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.web.api.dao.datamodel.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}