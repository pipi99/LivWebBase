package com.liv.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 组织机构表
 * </p>
 *
 * @author Liv
 * @since 2020-05-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("auth.organ")
@ApiModel(value="Organ对象", description="组织机构表")
public class Organ implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ORGAN_ID", type = IdType.AUTO)
    private Long organId;

    @ApiModelProperty(value = "机构名称")
    @TableField("ORGAN_NAME")
    @NotEmpty(message = "机构名称不能为空")
    private String organName;

    @ApiModelProperty(value = "上级机构ID")
    @TableField("PARENT_ID")
    private Long parentId;

    @ApiModelProperty(value = "机构级别")
    @TableField("ORGAN_LEVEL")
    @NotEmpty(message = "机构级别不能为空")
    private String organLevel;

    @ApiModelProperty(value = "机构类型")
    @TableField("ORGAN_TYPE")
    @NotEmpty(message = "机构类型不能为空")
    private String organType;

    @ApiModelProperty(value = "机构描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "数据状态")
    @TableField("STATE")
    private String state;


}
