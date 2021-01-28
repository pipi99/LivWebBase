package com.liv.api.base.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.dict
 * @Description: 数据字典实体
 * @date 2020.6.9  14:26
 * @email 453826286@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Dict", description = "数据字典")
@TableName("dict")
public class Dict extends BaseBean<Dict> {

    @TableId(type = IdType.ASSIGN_ID)
    @TableField("DICT_ID")
    private Long dictId;

    @TableField("D_TYPE")
    private String dType;

    @TableField("D_VALUE")
    private String dValue;

    @TableField("D_CODE")
    private String dCode;

    @TableField("PARENT_CODE")
    private String parentCode;
}
