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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Desktop")
@ApiModel(value = "Desktop",description = "桌面数据库实体表")
public class Desktop  implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 排序
     *
     * Nullable:  true
     */
    private Integer sort;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotEmpty(message="名称不能够为空")
    @ApiModelProperty("名称")
    private String alias;

    /**
     * 是否删除  1是  0否
     *
     * Nullable:  true
     */
    @TableLogic
    private Integer del=0;

    /**
     * 是否默认展示1是 0否，如果都为0 取第一个，如果默认多个为1 按排序取第一个
     *
     * Nullable:  true
     */
    private Integer isdefault=0;

    /**
     * 所树用户ID
     *
     * Nullable:  true
     */
    @NotNull(message="所属用户不能够为空")
    private Long userId;

    private static final long serialVersionUID = 1L;
}