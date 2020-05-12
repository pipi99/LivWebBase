package com.liv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.dao.datamodel.Menus;
import com.liv.dao.datamodel.Messages;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户消息service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface MessagesService extends IService<Messages>{
    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 根据用户ID查询桌面及消息列表
     **/
    public List<Messages> findByUserId(Long userId) throws Exception;
}
