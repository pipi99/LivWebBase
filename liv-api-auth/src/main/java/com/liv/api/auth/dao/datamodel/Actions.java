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
 * @Description: 操作操作
 * @date 2020.5.25  17:45
 * @email 453826286@qq.com
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Actions", description = "操作操作")
@TableName("auth.actions")
public class Actions {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    private Long actionId;

    /**
     * 操作名称
     **/
    private String actionName;
    /**
     * 操作权限
     **/
    private int permission;
    /**
     * 操作链接
     **/
    private String actionUrl;
    /**
     * 操作函数
     **/
    private String actionFun;
    /**
     * 上级ID
     **/
    private Long  menuId;

    /**
     * 图标
     **/
    private String icon;
    /**
     * 操作说明
     **/
    private String description;
    
}
