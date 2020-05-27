package com.liv.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * Table: user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User", description = "用户实体")
@TableName("auth.user")
public class User implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId
    private Long userId;

    /**
     * 用户名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "用户名称不能为空")
    private String userName;

    /**
     * 组织机构
     *
     * Nullable:  true
     */
    @NotEmpty(message = "所属组织机构不能为空")
    private Long orgId;

    /**
     * 姓名
     *
     * Nullable:  true
     */
    @NotBlank(message = "姓名不能为空")
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
    private Integer degree;

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
    private Date createDate;

    /**
     * 密码
     *
     * Nullable:  true
     */
    @NotBlank(message = "密码不能为空")
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
     * 锁定时间
     *
     * Nullable:  true
     */
    private Date locktime;

    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }

    private static final long serialVersionUID = 1L;
}