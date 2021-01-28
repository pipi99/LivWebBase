package com.liv.service.impl;
import com.liv.api.base.base.BaseService;
import com.liv.dao.TilesMapper;
import com.liv.dao.datamodel.Tile;
import com.liv.service.TilesService;
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
public class TilesServiceImpl extends BaseService<TilesMapper, Tile>  implements TilesService {

    /**
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图瓦片
     */
    @Override
    public Tile getSatelliteTile(Integer x,Integer y,Integer z) {
        return mapper.getSatelliteTile( x, y, z);
    }

    /**
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图混合瓦片
     */
    @Override
    public Tile getSatelliteMixedTile(Integer x,Integer y,Integer z) {
        return mapper.getSatelliteMixedTile( x, y, z);
    }
}
