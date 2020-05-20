package com.liv.web.api.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Table: messages  消息通知
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "MESSAGES_USERS")
public class MessagesUsers implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户主键
     *
     * Nullable:  false
     */
    @TableId
    private Long userId;
    /**
     * 消息主键
     *
     * Nullable:  false
     */
    @TableId
    private Long messageId;


    private static final long serialVersionUID = 1L;
}