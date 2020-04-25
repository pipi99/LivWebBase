package com.liv.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Table: desktop_elements
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("Desktop_Elements")
@ApiModel(value = "DesktopElements",description = "桌面元素数据库实体表")
public class DesktopElements implements Serializable {
    /**
     * 主键
     *
     * Nullable:  false
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     *
     * Nullable:  true
     */
    @NotEmpty(message = "名称不能为空")
    private String title;

    /**
     * 位置
     *
     * Nullable:  true
     */
    private Integer location;

    /**
     * 位置，所占网格的下标数组 a1,a2,a3..
     *
     * Nullable:  true
     */
    private String indexlocationarr;

    /**
     * 网格坐标，  x,y
     *
     * Nullable:  true
     */
    private String sizeXy;

    /**
     * 图标
     *
     * Nullable:  true
     */
    private String icons;

    /**
     * 是否可删除 1是  0否
     *
     * Nullable:  true
     */
    private Integer allowdel;

    /**
     * 是否允许重命名  1 是 0否
     *
     * Nullable:  true
     */
    private Integer allowrename;

    /**
     * 是否可拖动 1是  0否
     *
     * Nullable:  true
     */
    private Integer allowdrag;

    /**
     * 是否允许切换页面  1是 0否
     *
     * Nullable:  true
     */
    private Integer allowswitchpage;

    /**
     * 外键，桌面ID
     *
     * Nullable:  true
     */
    @NotNull
    private Long desktopId;

    /**
     * 内连接，外键 上级目录ID
     *
     * Nullable:  true
     */
    private Long parentFolderid;

    /**
     * 文件夹级别1，2，3，4 限制最多4级
     *
     * Nullable:  true
     */
    private Integer deep;

    /**
     * 链接
     *
     * Nullable:  true
     */
    private String iframeurl;

    /**
     * 桌面元素类型：folder ,app ,widget
     *
     * Nullable:  true
     */
    @NotBlank(message = "桌面元素类型不能为空")
    private String widgetType;

    private static final long serialVersionUID = 1L;
}