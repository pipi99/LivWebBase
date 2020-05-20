package com.liv.service.impl;

import com.liv.api.base.base.BaseService;
import com.liv.dao.MessagesUsersMapper;
import com.liv.dao.datamodel.MessagesUsers;
import com.liv.service.MessagesUsersService;
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
public class MessagesUsersServiceImpl extends BaseService<MessagesUsersMapper, MessagesUsers> implements MessagesUsersService {
}
