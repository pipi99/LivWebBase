package com.liv.api.auth.service.impl;

import com.google.common.collect.Lists;
import com.liv.api.auth.dao.MenuMapper;
import com.liv.api.auth.dao.UserMapper;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.service.MenuService;
import com.liv.api.base.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("apiMenuService")
public class MenuServiceImpl extends BaseService<MenuMapper, Menu> implements MenuService {
}
