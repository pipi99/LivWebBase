package com.liv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.liv.api.auth.utils.UserUtils;
import com.liv.api.base.base.BaseService;
import com.liv.dao.MessagesMapper;
import com.liv.dao.datamodel.Messages;
import com.liv.dao.datamodel.MessagesUsers;
import com.liv.service.MessagesService;
import com.liv.service.MessagesUsersService;
import com.liv.viewmodel.MessagesVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MessagesServiceImpl extends BaseService<MessagesMapper, Messages> implements MessagesService {

    @Autowired
    private MessagesUsersService messagesUsersService;
    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 根据用户ID查询菜单
     **/
    public List<MessagesVO> findByUserId(Long userId) throws Exception {
        //用户的菜单列表
        List<Messages> MessagesList = mapper.findByUserId(userId);
        List<MessagesVO> volist = Lists.newArrayList();
        for (int i = 0; i < MessagesList.size(); i++) {
            MessagesVO vo = new MessagesVO();
            BeanUtils.copyProperties(MessagesList.get(i),vo);
            volist.add(vo);
        }
        return volist;
    }

    /**
     * @param messageId
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 阅读消息
     */
    @Override
    public boolean view(Long messageId) throws Exception {
        Long userId = UserUtils.getCurrentUesr().getUser().getUserId();
        QueryWrapper<MessagesUsers> qw = new QueryWrapper<>();
        qw.eq("MESSAGE_ID",messageId);
        qw.eq("USER_ID",userId);
        return messagesUsersService.remove(qw);
    }

    /**
     * @param m
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 保存消息
     */
    @Override
    public boolean saveNewMsg(Messages m) throws Exception {
        String userIds = m.getUserIds();
        if(StringUtils.isEmpty(userIds)){
            throw new Exception("通知用户不能为空！");
        }
        this.save(m);

        //用户消息的关联表
        String[] userIdsArr = userIds.split(",");
        List<MessagesUsers> mus = Lists.newArrayList();
        for (int i = 0; i < userIdsArr.length; i++) {
            MessagesUsers mu = new MessagesUsers();
            mu.setMessageId(m.getId());
            mu.setUserId(Long.parseLong(userIdsArr[i]));
            mus.add(mu);
        }
        messagesUsersService.saveBatch(mus);
        return true;
    }

    /**
     * @param m
     * @Author: LiV
     * @Date: 2020.5.15 09:59
     * @Description: 消息过期处理
     */
    @Override
    public boolean expireMsg(Messages m) throws Exception {
        return false;
    }
}
