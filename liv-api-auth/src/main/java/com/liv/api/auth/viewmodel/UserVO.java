package com.liv.api.auth.viewmodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.Role;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Table: user
 */
@Data
public class UserVO implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    private Long userId;

    /**
     * 用户名称
     *
     * Nullable:  true
     */
    private String userName;

    /**
     * 组织机构
     *
     * Nullable:  true
     */
    private Long orgId;

    /**
     * 姓名
     *
     * Nullable:  true
     */
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
    private Date createDate;

    /**
     * @Author: LiV
     * @Date: 2020.4.19 20:42
     * @Description: 是否锁定  1是 0否
     **/
    private String locked;

    @Getter(AccessLevel.NONE)
    private Long[] groupIds;
    public Long[] getGroupIds(){
        Long[] ids = groupIds;
        if(groups!=null&&groups.size()>0){
            ids = this.groups.stream().map(groups1 -> groups1.getGroupId()).collect(Collectors.toList()).toArray(new Long[groups.size()]);
        }
        return ids;
    }

    @Getter(AccessLevel.NONE)
    private Long[] roleIds;
    public Long[] getRoleIds(){
        Long[] ids = roleIds;
        if(roles!=null&&roles.size()>0){
            ids = this.roles.stream().map(roles1 -> roles1.getRoleId()).collect(Collectors.toList()).toArray(new Long[roles.size()]);
        }
        return ids;
    }

    /**所属用户组*/
    private List<Groups> groups;

    /**所属角色*/
    private List<Role> roles;

    /**
     * 锁定时间
     *
     * Nullable:  true
     */
    private Date locktime;

    private static final long serialVersionUID = 1L;
}