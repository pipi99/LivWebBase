package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Table: user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User", description = "用户实体")
@TableName("SP_USER")
public class User extends BaseBean<User> implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(value = "USER_ID", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 用户名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "用户名称不能为空")
    @TableField("USER_NAME")
    private String userName;

    /**
     * 组织机构
     *
     * Nullable:  true
     */
    @TableField("ORG_ID")
    private Long orgId;

    /**
     * 姓名
     *
     * Nullable:  true
     */
    @NotBlank(message = "姓名不能为空")
    @TableField("ALIAS")
    private String alias;

    /**
     * 手机
     *
     * Nullable:  true
     */
    private Integer mobile;

    /**
     * 邮箱
     *
     * Nullable:  true
     */
    private String email;

    /**
     * 性别
     *
     * Nullable:  true
     */
    private String gender;

    /**
     * 学历
     *
     * Nullable:  true
     */
    private String degree;

    /**
     * 出生年月日
     *
     * Nullable:  true
     */
    private Date birthday;

    /**
     * 照片
     *
     * Nullable:  true
     */
    private String photo;

    /**
     * 创建日期
     *
     * Nullable:  true
     */
    @TableField("CREATE_DATE")
    private Date createDate;

    /**
     * 密码
     *
     * Nullable:  true
     */
    private String password;

    /**
     * Nullable:  true
     */
    private String salt;

    /**
     * @Author: LiV
     * @Date: 2020.4.19 20:42
     * @Description: 是否锁定  1是 0否
     **/
    private String locked;

    /**
     * 是否临时用户
     */
    private String temp = "0";
    /**
     * 锁定时间
     *
     * Nullable:  true
     */
    private Date locktime;

    @TableField(exist = false)
    private Long[] groupIds;

    @TableField(exist = false)
    private Long[] roleIds;

    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }

    private static final long serialVersionUID = 1L;
}