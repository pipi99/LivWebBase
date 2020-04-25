package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.DesktopSubject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DesktopSubjectMapper extends BaseMapper<DesktopSubject> {

}