package com.liv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.dao.datamodel.Menus;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户桌面元素service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface MenusService extends IService<Menus>{
    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 根据用户ID查询桌面及桌面元素列表
     **/
    public List<Menus> findByUserId(Long userId) throws Exception;
}
