package com.liv.api.auth.dao;

import com.liv.api.auth.entity.Tile;
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
@Repository
public interface TilesDao {
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