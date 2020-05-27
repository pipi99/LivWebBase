package com.liv.api.auth.domainmodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Table: user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RolePermissionDO", description = "角色权限DO实体（含资源）")
public class PermissionDO implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId
    private Long permissionId;

    /**
     * 权限类型： CRUD
     *
     * Nullable:  true
     */
    private String permission;
    /**
     * 资源ID
     *
     * Nullable:  true
     */
    private Long  resourceId;

    /**
     * 菜单主键
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
    private static final long serialVersionUID = 1L;
}