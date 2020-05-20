package com.liv.domainmodel;

import com.liv.dao.datamodel.Desktop;
import com.liv.dao.datamodel.DesktopElements;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "DesktopDO",description = "桌面元素DO")
public class DesktopDO extends Desktop implements Serializable {
    /**
     * @Description:  桌面元素
     **/
    @ApiModelProperty("桌面元素")
    private List<DesktopElements> elementsList;

    private static final long serialVersionUID = 1L;
}