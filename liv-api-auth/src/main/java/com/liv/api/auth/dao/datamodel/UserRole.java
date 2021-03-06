package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserRole", description = "角色关联关系表")
@TableName("USER_ROLE")
public class UserRole implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long roleId;
    private Long userId;

    private static final long serialVersionUID = 1L;
}