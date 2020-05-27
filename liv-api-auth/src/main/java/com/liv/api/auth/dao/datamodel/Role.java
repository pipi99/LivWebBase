package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.TableField;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Role", description = "角色实体")
@TableName("auth.role")
public class Role implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId
    private Long roleId;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "角色名称不能为空")
    @Pattern(regexp = "[a-zA-Z]+",message = "请输入正确的角色名称，英文字符")
    private String roleAlias;

    /**
     * 是否删除
     *
     * Nullable:  true
     */
    @TableLogic(value = "0",delval = "1")
    private Integer del;

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