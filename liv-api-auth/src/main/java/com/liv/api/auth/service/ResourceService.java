package com.liv.api.auth.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.dao.datamodel.Resource;
import com.liv.api.auth.domainmodel.MenuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 菜单service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface ResourceService extends IService<Resource>{

}
