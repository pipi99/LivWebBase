package com.liv.auth.service.impl;

import com.liv.api.base.base.BaseService;
import com.liv.auth.dao.AppMapper;
import com.liv.auth.dao.datamodel.App;
import com.liv.auth.service.AppService;
import org.springframework.stereotype.Service;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("AppService")
public class AppServiceImpl extends BaseService<AppMapper, App> implements AppService {
}
