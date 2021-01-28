package com.liv.api.auth.domainmodel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.base.base.BaseBean;
import com.liv.api.base.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.domainmodel
 * @Description: 用户查询类
 * @date 2020.6.9  10:11
 * @email 453826286@qq.com
 */
public class UserQuery extends User implements BaseQuery<User> {

    @Override
    public QueryWrapper<User> getQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(this.getAlias())){
            qw.like("ALIAS",this.getAlias());
        }
        if(this.getOrgId()!=null){
            qw.eq("ORG_ID",this.getOrgId());
        }
        return qw;
    }
}
