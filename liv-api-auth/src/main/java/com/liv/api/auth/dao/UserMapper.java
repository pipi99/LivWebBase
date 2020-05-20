package com.liv.api.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.api.auth.dao.datamodel.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component("apiUserMapper")
public interface UserMapper extends BaseMapper<User> {
}