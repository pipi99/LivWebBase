package com.liv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.dao.datamodel.Desktop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface DesktopMapper  extends BaseMapper<Desktop> {
    /**
     * @Author: LiV
     * @Date: 2020.4.17 18:19
     * @Description: 设置是否默认页面
     **/
    int updateDefaultByPrimaryKey(@Param("id") Long id);

    /***
     * @Author: LiV
     * @Date: 2020.4.17 18:22
     * @Description: 取消默认页面
     **/
    int updateNotDefault();
}