package com.liv.web.api.service.impl;

import com.liv.web.api.dao.MessagesUsersMapper;
import com.liv.web.api.dao.datamodel.MessagesUsers;
import com.liv.web.api.service.MessagesUsersService;
import com.liv.web.api.web.api.base.base.BaseService;
import org.springframework.stereotype.Service;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户消息service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service
public class MessagesUsersServiceImpl extends  BaseService<MessagesUsersMapper, MessagesUsers> implements MessagesUsersService {
}
