package com.liv.api.base.dicttype;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@Service("apiDictTypeService")
public class DictTypeServiceImpl extends BaseService<DictTypeMapper, DictType> implements DictTypeService {

    @Override
    public IPage<DictType> pagelist(DictTypeQuery query) {
        IPage<DictType> pageList = this.page(query.getPage(),query.getQueryWrapper());
        return pageList;
    }
}
