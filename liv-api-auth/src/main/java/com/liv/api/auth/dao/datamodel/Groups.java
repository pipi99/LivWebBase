package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Groups", description = "组实体")
@TableName("auth.Groups")
public class Groups implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId
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
    private Integer del;

    /**
     * 用户组说明
     *
     * Nullable:  true
     */
    private String description;

    private static final long serialVersionUID = 1L;
}