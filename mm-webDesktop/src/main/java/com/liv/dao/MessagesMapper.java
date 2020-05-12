package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.Messages;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {

}