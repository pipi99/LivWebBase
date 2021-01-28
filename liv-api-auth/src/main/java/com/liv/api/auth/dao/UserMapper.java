package com.liv.api.auth.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.liv.api.auth.dao.datamodel.Groups;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.auth.domainmodel.PermissionDO;
import com.liv.api.auth.domainmodel.UserQuery;
import com.liv.api.auth.viewmodel.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("apiUserMapper")
public interface UserMapper extends BaseMapper<User> {

    /**
     * @Author: LiV
     * @Date: 2020.5.26 11:30
     * @Description: 获取用户的权限
     **/
    public List<PermissionDO> findPermissionAndResources(Long userId);


    /**
     * @Author: LiV
     * @Date: 2020.5.26 11:30
     * @Description: 获取用户组
     **/
    @Select("select a.group_id as groupId,group_name as groupName,description,del  from @[dbschema]groups a,@[dbschema]user_group  b where a.GROUP_ID=b.GROUP_ID and del=0 and b.USER_ID =#{userId}")
    public List<Groups> findGroups(Long userId);

    /**
     * @Author: LiV
     * @Date: 2020.7.8 18:57
     * @Description: 查询用户 用户组和角色
     **/
    public IPage<UserVO> findUserGroupRole(IPage<User> page, @Param(Constants.WRAPPER) Wrapper<User> queryWrapper);


}