package com.liv.web.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.web.api.dao.datamodel.DesktopElements;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DesktopElementsMapper extends BaseMapper<DesktopElements> {

    @Select("select t.* from desktop_elements t where exists (select id from desktop d where d.id = t.desktop_id and d.user_id=#{userId})")
    List<DesktopElements> listByUserId(Long userId);
}