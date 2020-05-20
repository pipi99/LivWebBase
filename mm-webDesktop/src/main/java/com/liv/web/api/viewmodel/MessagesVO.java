package com.liv.web.api.viewmodel;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * Table: messages  消息通知
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagesVO implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    private Long id;

    /**
     * 名称
     *
     * Nullable:  true
     */
    private String title;

    /**
     * 内容
     *
     * Nullable:  true
     */
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
    private String msgFrom;

    /**
     * 有效期(天)  默认为  0 永不过期
     *
     * Nullable:  true
     */
    private Integer days=0;

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
    private Integer msgExpire;

    /**
     * 图标
     *
     * Nullable:  true
     */
    private String icons;

    private static final long serialVersionUID = 1L;
}