package com.liv.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.auth.dao.datamodel.Organ;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface OrganService extends IService<Organ>{
    public List<Organ> treelist() throws Exception;
}
