package com.liv.dao.datamodel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("dict")
@ApiModel(value = "Dict",description = "字典实体表")
public class Dict {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String dicttype;

    private String dcode;

    private String dname;

    private String remark;

}