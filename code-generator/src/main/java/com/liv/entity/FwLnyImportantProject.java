package com.liv.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 重点项目列表
 * </p>
 *
 * @author Liv
 * @since 2020-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("FW_LNY_IMPORTANT_PROJECT")
@ApiModel(value="FwLnyImportantProject对象", description="重点项目列表")
public class FwLnyImportantProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "施工单位")
    @TableField("CONSTRUCTION_UNIT")
    private String constructionUnit;

    @ApiModelProperty(value = "联系人")
    @TableField("CONTACTS")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    @TableField("PHONE")
    private BigDecimal phone;

    @ApiModelProperty(value = "项目阶段监测")
    @TableField("STAGE")
    private String stage;

    @ApiModelProperty(value = "用工用料研判")
    @TableField("JUDGE")
    private String judge;

    @ApiModelProperty(value = "项目类型")
    @TableField("P_TYPE")
    private String pType;

    @ApiModelProperty(value = "年份")
    @TableField("YEARS")
    private BigDecimal years;

    @ApiModelProperty(value = "维度")
    @TableField("LATITUDE")
    private Float latitude;

    @ApiModelProperty(value = "经度")
    @TableField("LONGITUDE")
    private Float longitude;

    @ApiModelProperty(value = "接电预警")
    @TableField("WARNING")
    private String warning;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;


}
