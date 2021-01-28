package com.liv.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 应用表
 * </p>
 *
 * @author Liv
 * @since 2020-05-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("auth.app")
@ApiModel(value="app", description="应用表")
@EqualsAndHashCode(callSuper = false)
public class App extends BaseBean<App> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "APP_ID", type = IdType.AUTO)
    private Long appId;

    @ApiModelProperty(value = "应用名称")
    @TableField("APP_NAME")
    @NotEmpty(message = "应用名称不能为空")
    private String appName;

    @ApiModelProperty(value = "应用标识")
    @TableField("APP_NAME")
    @NotEmpty(message = "应用名称不能为空")
    private String appCode;

    @ApiModelProperty(value = "应用描述")
    @TableField("DESCRIPTION")
    private String description;

}
