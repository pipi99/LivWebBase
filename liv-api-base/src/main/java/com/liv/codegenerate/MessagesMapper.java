package com.liv.codegenerate;

import com.liv.codegenerate.Messages;
import java.util.List;

public interface MessagesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Messages record);

    Messages selectByPrimaryKey(Long id);

    List<Messages> selectAll();

    int updateByPrimaryKey(Messages record);
}