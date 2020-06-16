package com.liv.api.base.dicttype;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author: LiV
 * @Date: 2020.6.9 14:31
 * @Description: 字典 service
 **/
public interface DictTypeService extends IService<DictType>{
    public IPage<DictType> pagelist(DictTypeQuery query);
}
