package com.liv.controller;

import com.liv.api.base.base.BaseController;
import com.liv.api.base.base.DataBody;
import com.liv.dao.LineMapper;
import com.liv.dao.TilesMapper;
import com.liv.dao.datamodel.Line;
import com.liv.dao.datamodel.Tile;
import com.liv.service.LineService;
import com.liv.service.TilesService;
import com.liv.util.ImageUtil;
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
@RequestMapping(value = "/bizc")
public class LineController extends BaseController<LineMapper, Line, LineService> {
    /**
     * @Author: LiV
     * @Date: 2020.8.11 09:32
     * @Description: 查询线路
     **/
    @RequestMapping(value="/getLines")
    public DataBody getLines(){
        return DataBody.success(this.service.getLines());
    }

}
