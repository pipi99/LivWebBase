package com.liv.web.api.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Menus")
@ApiModel(value = "Menus",description = "菜单、应用商店")
public class Menus implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 上级ID
     *
     * Nullable:  true
     */
    private Long pid;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotEmpty(message="名称不能够为空")
    @ApiModelProperty("名称")
    private String title;

    /**
     * 是否叶子节点  1是  0否
     *
     * Nullable:  true
     */
    private Integer isleaf;

    /**
     * 图标
     *
     * Nullable:  true
     */
    private String icons;

    /**
     * 所树用户ID
     *
     * Nullable:  true
     */
    @NotNull(message="所属用户不能够为空")
    private Long userId;

    private static final long serialVersionUID = 1L;
}