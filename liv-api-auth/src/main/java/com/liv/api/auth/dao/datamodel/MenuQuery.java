package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.auth.domainmodel.MenuDO;
import com.liv.api.base.base.BaseBean;
import com.liv.api.base.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.dao.datamodel
 * @Description: 菜单表
 * @date 2020.5.25  17:45
 * @email 453826286@qq.com
 */

public class MenuQuery extends Menu implements BaseQuery<Menu> {

    /**
     * 组装查询条件
     */
    @Override
    public QueryWrapper<Menu> getQueryWrapper() {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(this.getMenuName())){
            qw.like("MENU_NAME",this.getMenuName());
        }
        if(this.getAppId()!=null){
            qw.eq("APP_ID",this.getAppId());
        }
        return qw;
    }
}
