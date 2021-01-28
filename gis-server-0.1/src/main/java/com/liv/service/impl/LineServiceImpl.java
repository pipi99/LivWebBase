package com.liv.service.impl;
import com.liv.api.base.base.BaseService;
import com.liv.dao.LineMapper;
import com.liv.dao.TilesMapper;
import com.liv.dao.datamodel.Line;
import com.liv.dao.datamodel.Tile;
import com.liv.service.LineService;
import com.liv.service.TilesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 瓦片服务层
 * @date 2020.3.24  14:45
 * @email 453826286@qq.com
 */
@Service
public class LineServiceImpl extends BaseService<LineMapper, Line>  implements LineService {

    /**
     * @Author: LiV
     * @Date: 2020.8.11 09:32
     * @Description: 查询线路
     **/
    public List<Line> getLines(){
        return this.mapper.getLines();
    }
}
