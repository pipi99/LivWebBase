package com.liv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liv.dao.DesktopElementsMapper;
import com.liv.dao.datamodel.Desktop;
import com.liv.domainmodel.DesktopDO;

import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户桌面service
 * @date 2020.4.14  11:10
 * @email 453826286@qq.com
 */
public interface DesktopService extends IService<Desktop>{

    /**
     * @Author: LiV
     * @Date: 2020.4.17 18:13
     * @Description: 设置默认桌面
     **/
    boolean setDefault(Long id);

    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:31
     * @Description: 根据用户ID查询桌面及桌面元素列表
     **/
    public List<DesktopDO> findByUserId(Long userId) throws Exception;
}
