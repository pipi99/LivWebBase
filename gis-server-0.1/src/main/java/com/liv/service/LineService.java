package com.liv.service;

import com.liv.dao.datamodel.Line;
import com.liv.dao.datamodel.Tile;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 瓦片服务层
 * @date 2020.3.24  14:41
 * @email 453826286@qq.com
 */
public interface LineService {
    public List<Line> getLines();
}
