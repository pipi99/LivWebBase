package com.liv.web.api.domainmodel;

import com.liv.web.api.dao.datamodel.DesktopElements;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.domainmodel
 * @Description: 桌面元素
 * @date 2020.4.18  13:28
 * @email 453826286@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DesktopElementsDO",description = "桌面元素DO表")
public class DesktopElementsDO extends DesktopElements implements Serializable {
    /**
     * @Author: LiV
     * @Date: 2020.4.18 13:29
     * @Description: 文件夹类型的具有下级元素
     **/
    @ApiModelProperty("子桌面元素")
    private List<DesktopElements> children;
}
