package com.liv.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.auth.dao.datamodel.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}