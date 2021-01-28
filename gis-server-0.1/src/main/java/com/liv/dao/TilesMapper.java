package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.Tile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.dao
 * @Description: 瓦片数据存储层
 * @date 2020.3.24  14:48
 * @email 453826286@qq.com
 */
@Mapper
public interface TilesMapper extends BaseMapper<Tile> {
    /**
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图瓦片
     **/
    public Tile getSatelliteTile(@Param("x") Integer x, @Param("y")Integer y, @Param("z")Integer z);

    /**
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图混合瓦片
     **/
    public Tile getSatelliteMixedTile(@Param("x")Integer x,@Param("y")Integer y,@Param("z")Integer z);
}