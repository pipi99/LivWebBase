package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "菜单名称不能为空")
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
    /**
     * 菜单图标
     **/
    private String icon;

    /**
     * 访问控制：login： 登录后访问。 perm：授权后访问。anon：无需登录或授权访问
     **/
    private String accessCtrl;
}
