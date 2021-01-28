package com.liv.api.auth.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liv.api.base.base.BaseBean;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.dao.datamodel
 * @Description: 资源表
 * @date 2020.5.25  17:45
 * @email 453826286@qq.com
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Resource", description = "资源实体")
@TableName("RESOURCES")
public class Resource extends BaseBean<Resource> {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long resourceId;

    /**
     * 资源名称
     **/
    @NotEmpty(message = "资源名称不能为空")
    private String resourceName;
    /**
     * 上级ID
     **/
    private Long  parentId;

    /**
     * 所属应用
     **/
    private Long  appId;
    /**
     * 资源排序
     **/
    private int sort;

    /**
     * 资源说明
     **/
    private String description;
    /**
     * 资源链接
     **/
    private String path;
    /**
     * 资源图标
     **/
    private String icon;

    /**
     * 访问控制：login： 登录后访问。 perm：授权后访问。anon：无需登录或授权访问
     **/
    private String accessCtrl;

    private int resourceType;

    private String permissionStr;

    private String routerName;

    private String routerPath;

    private String routerComp;

    private String routerCache;
}
