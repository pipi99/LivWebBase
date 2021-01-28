package com.liv.dao.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv
 * @Description: 瓦片
 * @date 2020.3.24  14:53
 * @email 453826286@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line {

    private String id;
    private String name;
    private String voltageType;
    private Point start;
    private Point end;
    private double length;
    private List<Point> points;
}
