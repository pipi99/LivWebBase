package com.liv.api.auth.domainmodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.liv.api.auth.App;
import com.liv.api.auth.dao.datamodel.Actions;
import com.liv.api.auth.dao.datamodel.Menu;
import com.liv.api.auth.utils.AppConst;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.Iterator;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.domainmodel
 * @Description: 菜单 逻辑模型
 * @date 2020.6.5  14:56
 * @email 453826286@qq.com
 */
@Data
public class MenuDO {
    /**
     * 主键
     *
     * Nullable:  false
     */
    private Long menuId;
    /**
     * 所属应用
     *
     * Nullable:  false
     */
    private Long appId;
    /**
     * 权限类型： CRUD
     *
     * Nullable:  true
     */
    private int permission = AppConst.MAX_BIT_PERMISSION;

    /**
     * 菜单名称
     **/
    private String menuName;
    /**
     * 上级ID
     **/
    private Long  parentId;
    /**
     * 菜单访问控制  perm  login  open
     **/
    private String accessCtrl;

    /**
     * 菜单说明
     **/
    private String description;
    /**
     * 菜单链接
     **/
    private String mUrl;
    /**
     * 菜单图标
     **/
    private String icon;

    /**
     * 菜单排序
     **/
    private int sort;

    /**
     * 是否叶子节点
     **/
    private int isLeaf;
    /**
     * 下级菜单
     **/
    private List<MenuDO> children;

    /**
     * 菜单下的所有权限操作
     **/
    @Getter(AccessLevel.NONE)
    private List<Actions> actions;

    public List<Actions> getActions() {
        //无需权限控制的菜单，赋予最大权限位数
        //也就是具备菜单下的所有资源
        if(!AppConst.MENU_PERM.equals(this.accessCtrl)){
            this.permission = AppConst.MAX_BIT_PERMISSION;
        }
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
}
