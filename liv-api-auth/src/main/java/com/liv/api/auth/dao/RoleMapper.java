package com.liv.api.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.api.auth.dao.datamodel.Role;
import com.liv.api.auth.domainmodel.PermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("apiRoleMapper")
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * @Author: LiV
     * @Date: 2020.5.26 11:30
     * @Description: 获取角色的权限
     **/
    public List<PermissionDO> findPermissionAndResources(Long roleId);

    @Select("select r.role_name as roleName,r.role_alias as roleAlias,r.ROLE_ID as roleId ,r.create_date as createDate ,r.description  from @[dbschema]role r,@[dbschema]USER_ROLE ur where r.ROLE_ID = ur.role_id and r.DEL=0 and ur.user_id = #{userId}")
    public List<Role> findByUserId(Long userId);

    @Select("select r.role_name as roleName,r.role_alias as roleAlias,r.ROLE_ID as roleId ,r.create_date as createDate ,r.description  from @[dbschema]role r,@[dbschema]GROUP_ROLE gr where r.ROLE_ID = gr.role_id and r.DEL=0 and gr.group_id = #{groupId}")
    public List<Role> findByGroupId(Long groupId);

    public List<Role> findByGroupIds(List<Long> groupIds);
}