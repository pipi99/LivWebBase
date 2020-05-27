package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.dao.datamodel
 * @Description: 菜单表
 * @date 2020.5.25  17:45
 * @email 453826286@qq.com
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Menu", description = "菜单实体")
@TableName("auth.menu")
public class Menu {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     **/
    private String menuName;
    /**
     * 上级ID
     **/
    private Long  parentId;


    /**
     * 菜单说明
     **/
    private String description;
    /**
     * 菜单链接
     **/
    private String mUrl;
}
