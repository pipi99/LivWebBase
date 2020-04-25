package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.DesktopElements;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DesktopElementsMapper extends BaseMapper<DesktopElements> {

    @Select("select t.* from desktop_elements t where exists (select id from desktop d where d.id = t.desktop_id and d.user_id=#{userId})")
    List<DesktopElements> listByUserId(Long userId);
}