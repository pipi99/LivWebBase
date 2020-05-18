package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.Messages;
import com.liv.dao.datamodel.MessagesUsers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface MessagesUsersMapper extends BaseMapper<MessagesUsers> {

}