package com.liv.web.api.controller;

import com.liv.web.api.base.LivBaseController;
import com.liv.web.api.entity.Tile;
import com.liv.web.api.service.TilesService;
import com.liv.web.api.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.controller
 * @Description: 瓦片控制器
 * @date 2020.3.24  14:41
 * @email 453826286@qq.com
 */
@RestController
@RequestMapping(value = "/tiles")
public class TilesController extends LivBaseController {

    @Autowired
    private TilesService tilesService;
    /**
     * @param x y z
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图瓦片
     */
    @RequestMapping(value="/satellitetile/{x}/{y}/{z}")
    public void getSatelliteTile(@PathVariable("x")Integer x, @PathVariable("y")Integer y, @PathVariable("z")Integer z) throws IOException {
        Tile tile = tilesService.getSatelliteTile(x, y, z);
        if(tile!=null){
            ImageUtil.gzipResponseOut(tile.getTile(),this.getResponse());
        }else{
            ImageUtil.gzipNoTileResponseOut(this.getResponse());
        }
    }

    /**
     * @param x y z
     * @Author: LiV
     * @Date: 2020.3.25 09:17
     * @Description: 卫星图混合瓦片
     */
    @RequestMapping(value="/satellitemixedtile/{x}/{y}/{z}")
    public void getSatelliteMixedTile(@PathVariable("x")Integer x,@PathVariable("y")Integer y,@PathVariable("z")Integer z) throws IOException {
        Tile tile = tilesService.getSatelliteMixedTile(x, y, z);
        if(tile!=null){
            ImageUtil.gzipResponseOut(tile.getTile(),this.getResponse());
        }else{
            ImageUtil.gzipNoTileResponseOut(this.getResponse());
        }
    }
}
