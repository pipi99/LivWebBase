package com.liv.api.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.domainmodel.MenuDO;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 菜单service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface MenuService extends IService<Menu>{
    public void setPermissionFilter();
    public List<MenuDO> getCurUserMenus() throws Exception;
}
