package com.liv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.MessagesMapper;
import com.liv.dao.datamodel.Messages;
import com.liv.service.MessagesService;
import com.liv.web.api.base.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户消息service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service
public class MessagesServiceImpl extends BaseService<MessagesMapper, Messages> implements MessagesService{
    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 根据用户ID查询菜单
     **/
    public List<Messages> findByUserId(Long userId) throws Exception {
        //用户的菜单列表
        QueryWrapper<Messages> qw = new QueryWrapper<>();
        qw.like("USER_IDS",userId);
        List<Messages> MessagesList = mapper.selectList(qw);

        return MessagesList;
    }
}
