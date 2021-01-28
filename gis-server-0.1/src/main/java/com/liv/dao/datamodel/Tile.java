package com.liv.dao.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class Tile {
    private Integer type;
    private Integer x;
    private Integer y;
    private Integer z;
    private byte[] tile;
}
