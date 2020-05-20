package com.liv.web.api.dao.datamodel;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Table: messages  消息通知
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "MESSAGES")
public class Messages implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotBlank(message = "消息名称不能为空")
    private String title;

    /**
     * 内容
     *
     * Nullable:  true
     */
    @NotBlank(message = "消息内容不能为空")
    private String content;

    /**
     * 链接
     *
     * Nullable:  true
     */
    private String url;

    /**
     * 消息来源描述
     *
     * Nullable:  true
     */
    @NotBlank(message = "消息来源不能为空")
    private String msgFrom;

    /**
     * 有效期(天)  默认为  -1 永不过期
     *
     * Nullable:  true
     */
    private Integer days=-1;

    /**
     * 消息日期
     *
     * Nullable:  true
     */
    private Date createdate;

    /**
     * 是否过期 1是 0否   默认为 0 否
     *
     * Nullable:  true
     */
    @TableLogic(value = "0",delval = "1")
    private Integer msgExpire=0;

    /**
     * 图标
     *
     * Nullable:  true
     */
    private String icons;

    /**
     * @Author: LiV
     * @Date: 2020.5.15 09:55
     * @Description: 逻辑字段，存入消息时的用户ID,多个逗号分隔
     **/
    @TableField(exist = false)
    @NotEmpty(message = "通知用户不能为空")
    private String userIds;

    private static final long serialVersionUID = 1L;
}