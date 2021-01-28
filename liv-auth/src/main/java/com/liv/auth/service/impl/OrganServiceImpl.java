package com.liv.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.api.base.base.BaseService;
import com.liv.api.base.utils.LivCollectionUtils;
import com.liv.auth.dao.OrganMapper;
import com.liv.auth.dao.datamodel.Organ;
import com.liv.auth.service.OrganService;
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
@Service("organService")
public class OrganServiceImpl extends BaseService<OrganMapper, Organ> implements OrganService {


    @Override
    public List<Organ> treelist() throws Exception {
        List<Organ> list = mapper.selectList(new QueryWrapper<>());
        list = LivCollectionUtils.getTree(list,"organId","parentId","children");
        return list;
    }
}
