package com.liv.api.auth.service.impl;

import com.liv.api.auth.dao.ResourceMapper;
import com.liv.api.auth.dao.datamodel.Resource;
import com.liv.api.auth.service.ResourceService;
import com.liv.api.base.base.BaseService;
import org.springframework.stereotype.Service;


/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户 service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service("apiResourceService")
public class ResourceServiceImpl extends BaseService<ResourceMapper, Resource> implements ResourceService {

}
