package com.liv.api.auth.service;

import com.liv.api.auth.entity.Tile;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 瓦片服务层
 * @date 2020.3.24  14:41
 * @email 453826286@qq.com
 */
public interface TilesService {
    /**
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图瓦片
     **/
    public Tile getSatelliteTile(Integer x,Integer y,Integer z);

    /**
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图混合瓦片
     **/
    public Tile getSatelliteMixedTile(Integer x,Integer y,Integer z);
}
