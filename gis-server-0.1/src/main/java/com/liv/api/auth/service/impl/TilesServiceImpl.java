package com.liv.api.auth.service.impl;
import com.liv.api.auth.dao.TilesDao;
import com.liv.api.auth.entity.Tile;
import com.liv.api.auth.service.TilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 瓦片服务层
 * @date 2020.3.24  14:45
 * @email 453826286@qq.com
 */
@Service
public class TilesServiceImpl implements TilesService {

    @Autowired
    private TilesDao tilesDao;
    /**
     * @param tile
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图瓦片
     */
    @Override
    public Tile getSatelliteTile(Integer x,Integer y,Integer z) {
        return tilesDao.getSatelliteTile( x, y, z);
    }

    /**
     * @param tile
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图混合瓦片
     */
    @Override
    public Tile getSatelliteMixedTile(Integer x,Integer y,Integer z) {
        return tilesDao.getSatelliteMixedTile( x, y, z);
    }
}
