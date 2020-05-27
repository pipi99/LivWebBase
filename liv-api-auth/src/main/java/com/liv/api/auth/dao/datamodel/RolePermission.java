package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * Table: user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RolePermission", description = "角色权限实体")
@TableName("auth.role_permission")
public class RolePermission implements Serializable {
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

    private static final long serialVersionUID = 1L;
}