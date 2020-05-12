package com.liv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.MenusMapper;
import com.liv.dao.datamodel.Menus;
import com.liv.service.MenusService;
import com.liv.web.api.base.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户菜单、应用service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service
public class MenusServiceImpl extends BaseService<MenusMapper, Menus> implements MenusService{
    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 根据用户ID查询菜单
     **/
    public List<Menus> findByUserId(Long userId) throws Exception {
        //用户的菜单列表
        QueryWrapper<Menus> qw = new QueryWrapper<>();
        qw.eq("USER_ID",userId);
        List<Menus> menusList = mapper.selectList(qw);

        return menusList;
    }
}
