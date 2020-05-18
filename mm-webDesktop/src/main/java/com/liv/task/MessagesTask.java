package com.liv.task;

import com.liv.dao.MessagesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.task
 * @Description: 消息过期处理定时器
 * @date 2020.5.15  11:30
 * @email 453826286@qq.com
 */
@Configuration
@EnableScheduling
@Slf4j
public class MessagesTask {

    @Autowired
    private MessagesMapper mapper;
    //3.添加定时任务

    //或直接指定时间间隔，毫秒值
    /**
     * @Author: LiV
     * @Date: 2020.5.15 11:34
     * @Description: 处理过期的消息 ,每天23 点一次
     **/
    @Scheduled(cron = "0 0 23 * * ?")
    private void expireMsg() {
        log.info("处理过期的消息 ,每天23 点，消息通知过期处理: " );
        mapper.expireMsg();
    }

    /**
     * @Author: LiV
     * @Date: 2020.5.15 11:34
     * @Description: 删除无效信息,每周日晚上 23 点
     **/
    @Scheduled(cron = "0 0 23 ? * SUN")
    private void delMsg() {
        log.info("每周日晚上 23 点，删除已读或者已过期消息通知: " );
        mapper.delExpireAndAllReadedMsg();
    }
}
