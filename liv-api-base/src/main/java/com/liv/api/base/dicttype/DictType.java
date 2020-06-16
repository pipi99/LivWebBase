package com.liv.api.base.dicttype;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.dict
 * @Description: 数据字典类型实体
 * @date 2020.6.9  14:26
 * @email 453826286@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DictType", description = "数据字典类型")
@TableName("auth.dict_type")
public class DictType {

    @TableId(type = IdType.AUTO)
    @TableField("DICTTYPE_ID")
    private Long dicttypeId;

    @TableField("D_TYPE_NAME")
    private String dTypeName;

    @TableField("D_TYPE")
    private String dType;

}
