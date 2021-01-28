package com.liv.sp.dao.datamodel;

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
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.sp.dao.datamodel
 * @Description: java bean
 * @date 2020.12.11  10:52
 * @email 453826286@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("auth.sp")
@ApiModel(value="sp", description="java bean demo")
@EqualsAndHashCode(callSuper = false)
public class Sp  extends BaseBean<Sp> implements Serializable {

    private static final long serialVersionUID = 1L;

    /*主键*/
    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /*名称*/
    @NotNull
    @Length(min=10,max=100,message = "名称长度需要在10和100之间")
    @TableField(value = "NAME")
    private String name;

    /*说明*/
    @NotEmpty
    @Length(max=400)
    @TableField(value = "REMARK")
    private String remark;

    /*创建时间*/
    @TableField(value = "CREATE_TIME")
    private Date createTime;
}
