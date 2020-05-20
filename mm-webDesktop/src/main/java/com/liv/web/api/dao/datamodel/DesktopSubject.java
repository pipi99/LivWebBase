package com.liv.web.api.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Desktop_Subject")
@ApiModel(value = "DesktopSubject",description = "用户桌面主题实体表")
public class DesktopSubject {

    @TableId(type= IdType.AUTO)
    private Long id;

    @NotEmpty(message = "选择的主题ID不能为空")
    private Integer subject;

    private String bgcolor;

    private Integer bgimg;

    @NotEmpty(message = "用户ID不能为空")
    private Long userId;
    @NotEmpty(message = "桌面分辨率不能为空")
    private String desktopResolution;
}