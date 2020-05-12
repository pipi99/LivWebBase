package com.liv.controller;

import com.liv.dao.MessagesMapper;
import com.liv.dao.datamodel.Messages;
import com.liv.service.MessagesService;
import com.liv.web.api.base.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 消息通知管理控制器
 **/
@RestController
@RequestMapping(value = "Messages")
@Api(tags = "用户消息通知")
public class MessagesController extends BaseController<MessagesMapper,Messages, MessagesService> {

    @ApiOperation(value = "查询用户消息通知", notes="根据给定的用户ID 查询用户消息通知")
    @ApiImplicitParam(name = "userId", value = "当前登录用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
    @GetMapping(value="/{userId}")
    public List<Messages> list(@PathVariable("userId") Long userId) throws Exception {
        return service.findByUserId(userId);
    }
}
