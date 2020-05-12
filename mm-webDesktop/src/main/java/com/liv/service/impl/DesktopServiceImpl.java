package com.liv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liv.dao.DesktopElementsMapper;
import com.liv.dao.DesktopMapper;
import com.liv.dao.datamodel.Desktop;
import com.liv.dao.datamodel.DesktopElements;
import com.liv.domainmodel.DesktopDO;
import com.liv.domainmodel.DesktopElementsDO;
import com.liv.service.DesktopService;
import com.liv.web.api.base.utils.LivCllectionUtils;
import com.liv.web.api.base.base.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.service.impl
 * @Description: 用户桌面service
 * @date 2020.4.14  11:11
 * @email 453826286@qq.com
 */
@Service
public class DesktopServiceImpl extends BaseService<DesktopMapper,Desktop > implements DesktopService {

    /**
     * @Author: LiV
     * @Date: 2020.4.17 20:28
     * @Description: 页面元素mapper
     **/
    @Autowired
    private DesktopElementsMapper desktopElementsMapper;
    /**
     * @param id
     * @Author: LiV
     * @Date: 2020.4.17 18:13
     * @Description: 设置默认桌面
     */
    @Override
    public boolean setDefault(Long id) {
            mapper.updateNotDefault();
        return mapper.updateDefaultByPrimaryKey(id)>0;
    }

    /**
     * @param userId
     * @Author: LiV
     * @Date: 2020.4.17 18:13
     * @Description: 查询用户 的桌面及元素列表
     */
    @Override
    public List<DesktopDO> findByUserId(Long userId) throws Exception {
        //桌面元素
        List<DesktopElements> desktopElements = desktopElementsMapper.listByUserId(userId);
        //Entity to DO
        List<DesktopElementsDO> desktopElementsDO= desktopElements.stream().map(item ->{
            DesktopElementsDO ddo = new DesktopElementsDO();
            BeanUtils.copyProperties(item,ddo);
            return ddo;
        }).collect(Collectors.toList());

        //上下级组装children
        List<DesktopElementsDO> desktopElementsWithChindren = LivCllectionUtils.getTree(desktopElementsDO,"id","parentFolderid","children");
        //按照桌面分组
        Map<Long,List<DesktopElements>> desktopChildren = desktopElementsWithChindren.stream().collect(Collectors.groupingBy(DesktopElements::getDesktopId));

        //桌面列表
        QueryWrapper<Desktop> qw = new QueryWrapper<>();
        qw.eq("USER_ID",userId);
        qw.orderByAsc("sort");
        List<Desktop> desktopList = mapper.selectList(qw);

        //Entity to DO
        List<DesktopDO> dos= desktopList.stream().map(item ->{
            DesktopDO ddo = new DesktopDO();
            BeanUtils.copyProperties(item,ddo);
            ddo.setElementsList(desktopChildren.get(ddo.getId()));
            return ddo;
        }).collect(Collectors.toList());;

        return dos;
    }
}
