package com.liv.api.base.dicttype;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseBean;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.base.dict
 * @Description: 数据字典类型实体
 * @date 2020.6.9  14:26
 * @email 453826286@qq.com
 */
@Data
public class DictTypeQuery extends BaseBean<DictType> {

    private Long dicttypeId;

    private String dTypeName;

    private String dType;

    /**
     * 组装查询条件
     */
    @Override
    protected QueryWrapper<DictType> getQueryWrapper() {
        QueryWrapper<DictType> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(this.dTypeName)){
            qw.like("D_TYPE_NAME",this.dTypeName);
        }
        if(StringUtils.isNotEmpty(this.dType)){
            qw.like("D_TYPE",this.dType);
        }
        return qw;
    }
}
