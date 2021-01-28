package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.Line;
import com.liv.dao.datamodel.Tile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.dao
 * @Description: 瓦片数据存储层
 * @date 2020.3.24  14:48
 * @email 453826286@qq.com
 */
@Mapper
public interface LineMapper extends BaseMapper<Line> {
    public List<Line> getLines();
}