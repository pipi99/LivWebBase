package com.liv.api.auth.domainmodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liv.api.auth.dao.datamodel.Actions;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Table: user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RolePermissionDO", description = "角色权限DO实体（含资源）")
public class PermissionDO implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId
    private Long permissionId;

    /**
     * 权限类型： CRUD
     *
     * Nullable:  true
     */
    private int permission;
    /**
     * 资源ID
     *
     * Nullable:  true
     */
    private Long  resourceId;

    /**
     * 菜单主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     **/
    private String menuName;
    /**
     * 上级ID
     **/
    private Long  parentId;


    /**
     * 菜单说明
     **/
    private String description;
    /**
     * 菜单链接
     **/
    private String mUrl;

    /**
     * 菜单下的所有权限操作
     **/
    @Getter(AccessLevel.NONE)
    private List<Actions> actions;

    public List<Actions> getActions() {
        if(actions!=null&&actions.size()>0){
            Iterator<Actions> it = actions.iterator();
            while (it.hasNext()){
                Actions action = it.next();
                //无权限
                if((this.permission&action.getPermission())==0){
                    it.remove();
                }
            }
        }
        return this.actions;
    }

    private static final long serialVersionUID = 1L;
}