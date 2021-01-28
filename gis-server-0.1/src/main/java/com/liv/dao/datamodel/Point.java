package com.liv.dao.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
public class Point {
    private String id;
    private String name;
    private BigDecimal longitude;   //经度
    private BigDecimal latitude;    //维度
    private Integer order;
}
