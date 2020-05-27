package com.liv.auth.service.impl;

import com.liv.api.base.base.BaseService;
import com.liv.auth.dao.UserMapper;
import com.liv.auth.dao.datamodel.User;
import com.liv.auth.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("userService")
public class UserServiceImpl extends BaseService<UserMapper, User> implements UserService {



}
