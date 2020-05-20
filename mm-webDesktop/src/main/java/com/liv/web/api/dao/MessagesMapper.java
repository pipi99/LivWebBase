package com.liv.web.api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liv.web.api.dao.datamodel.Messages;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {

    /**
     * @Author: LiV
     * @Date: 2020.5.15 10:28
     * @Description: 查询用户未过期的消息
     **/
    @Select("select * from messages t1,messages_users t2 where t1.id =t2.message_id and msg_expire=0 and t2.user_id = #{userId}")
    public List<Messages> findByUserId(Long userId);

    /**
     * @Author: LiV
     * @Date: 2020.5.15 10:28
     * @Description: 更新消息状态为过期，每天
     **/
    @Select("update mmwebdesktop.messages set MSG_EXPIRE =1 where MSG_EXPIRE=0 and DAYS>0 and DATEDIFF(CREATEDATE,sysdate()) <days;")
    public void expireMsg();

    /**
     * @Author: LiV
     * @Date: 2020.5.15 10:28
     * @Description: 删除无效消息，读完，已过期 。每周
     **/
    @Select("delete from mmwebdesktop.messages where MSG_EXPIRE=1 or id not in (select a.id from (select distinct t1.id from  mmwebdesktop.messages t1,messages_users t2 where t2.message_id = t1.id) a)")
    public void delExpireAndAllReadedMsg();
}