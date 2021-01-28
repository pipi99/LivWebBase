package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.*;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Groups", description = "组实体")
@TableName("Groups")
public class Groups extends BaseBean<Groups> implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long groupId;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "名称不能为空")
    private String groupName;
    /**
     * 是否删除
     *
     * Nullable:  true
     */
    @TableLogic(value = "0",delval = "1")
    private Integer del =0;

    /**
     * 用户组说明
     *
     * Nullable:  true
     */
    private String description;

    @Getter(AccessLevel.NONE)
    @TableField(exist = false)
    Long[] roleIds;
    public Long[] getRoleIds(){
        if((roleIds==null||roleIds.length==0)&&roles!=null&&roles.size()>0){
            return roles.stream().map(role -> role.getRoleId()).collect(Collectors.toList()).toArray(new Long[roles.size()]);
        }
        return roleIds;
    }
    @TableField(exist = false)
    List<Role> roles;

    private static final long serialVersionUID = 1L;
}