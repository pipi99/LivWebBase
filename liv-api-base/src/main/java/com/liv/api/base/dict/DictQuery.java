package com.liv.api.base.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
public class DictQuery extends BaseBean<Dict> {

    @TableId(type = IdType.AUTO)
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

    /**
     * 组装查询条件
     */
    @Override
    protected QueryWrapper<Dict> getQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(this.dType)){
            qw.eq("D_TYPE",this.dType);
        }
        if(StringUtils.isNotEmpty(this.dValue)){
            qw.like("D_VALUE",this.dValue);
        }
        if(StringUtils.isNotEmpty(this.dCode)){
            qw.like("D_CODE",this.dCode);
        }
        return qw;
    }
}
