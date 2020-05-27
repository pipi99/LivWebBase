package com.liv.api.auth.controller;

import com.liv.api.auth.web.api.base.annotation.ValidResult;
import com.liv.api.auth.web.api.base.base.BaseController;
import com.liv.api.auth.web.api.base.base.ResultBody;
import com.liv.api.auth.dao.MessagesMapper;
import com.liv.api.auth.dao.datamodel.Messages;
import com.liv.api.auth.service.MessagesService;
import com.liv.api.auth.utils.ApiAuthUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: LiV
 * @Date: 2020.4.13 16:58
 * @Description: 消息通知管理控制器
 **/
@RestController
@RequestMapping(value = "messages")
@Api(tags = "用户消息通知")
public class MessagesController extends BaseController<MessagesMapper,Messages, MessagesService> {

    @ApiOperation(value = "查询用户消息通知", notes="根据给定的用户ID 查询用户消息通知")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path",defaultValue = "0")
    @GetMapping(value="/listByUserID")
    public ResultBody list(@PathVariable("userId") Long userId) throws Exception {
        return ResultBody.success(service.findByUserId(userId));
    }

    @ApiOperation(value = "查询当前登录用户消息通知", notes="查询当前登录用户消息通知")
    @GetMapping(value="/listCurrUser")
    public ResultBody list() throws Exception {
        Long userId = ApiAuthUtils.getCurrentUesr().getUser().getUserId();
        return ResultBody.success(service.findByUserId(userId));
    }

    @ApiOperation(value = "存入消息通知", notes="存入消息通知")
    @PostMapping(value="/save")
    @ValidResult
    public ResultBody save(@RequestBody(required = true) @Valid Messages m, BindingResult result) throws Exception {
        return ResultBody.success(service.saveNewMsg(m));
    }

    @ApiOperation(value = "阅读消息", notes="阅读消息")
    @PutMapping(value="/view/{messageId}")
    public ResultBody view(@PathVariable("messageId") Long messageId) throws Exception {
        return ResultBody.success(service.view(messageId));
    }
}
