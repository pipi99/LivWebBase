package com.liv.web.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.web.api.dao.datamodel.MessagesUsers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface MessagesUsersMapper extends BaseMapper<MessagesUsers> {

}