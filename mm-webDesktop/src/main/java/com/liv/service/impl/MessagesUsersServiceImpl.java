package com.liv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.MessagesMapper;
import com.liv.dao.MessagesUsersMapper;
import com.liv.dao.datamodel.Messages;
import com.liv.dao.datamodel.MessagesUsers;
import com.liv.service.MessagesService;
import com.liv.service.MessagesUsersService;
import com.liv.utils.UserUtils;
import com.liv.web.api.base.base.BaseService;
import org.apache.commons.lang3.StringUtils;
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
public class MessagesUsersServiceImpl extends  BaseService<MessagesUsersMapper, MessagesUsers> implements MessagesUsersService {
}
