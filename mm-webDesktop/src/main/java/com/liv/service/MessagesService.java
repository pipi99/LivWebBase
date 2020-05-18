package com.liv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.dao.datamodel.Menus;
import com.liv.dao.datamodel.Messages;
import com.liv.viewmodel.MessagesVO;

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
    public List<MessagesVO> findByUserId(Long userId) throws Exception;
    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 阅读消息
     **/
    public boolean view(Long messageId) throws Exception;

    /**
     * @Author: LiV
     * @Date: 2020.5.15 09:59
     * @Description: 保存新消息
     **/
    public boolean saveNewMsg(Messages m) throws Exception;

    /**
     * @Author: LiV
     * @Date: 2020.5.15 09:59
     * @Description: 消息过期处理 ，所有用户通知完处理
     **/
    public boolean expireMsg(Messages m) throws Exception;
}
