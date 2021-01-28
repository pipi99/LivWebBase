package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.*;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Role", description = "角色实体")
@TableName("role")
public class Role extends BaseBean<Role> implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type=IdType.ASSIGN_ID)
    private Long roleId;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "角色名称不能为空")
    @Pattern(regexp = "[a-zA-Z0-9]+",message = "请输入正确的角色名称，英文数字组合")
    private String roleName;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleAlias;

    /**
     * 是否删除
     *
     * Nullable:  true
     */
    @TableLogic(value = "0",delval = "1")
    private Integer del = 0;

    /**
     * 角色说明
     *
     * Nullable:  true
     */
    private String description;

    /**
     * 创建日期
     *
     * Nullable:  true
     */
    private Date createDate;


    private static final long serialVersionUID = 1L;
}