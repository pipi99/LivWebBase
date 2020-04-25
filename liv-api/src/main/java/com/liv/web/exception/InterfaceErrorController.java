package com.liv.web.exception;


import com.alibaba.fastjson.JSONObject;
import com.liv.web.base.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: 处理404接口异常
 * @date 2020.4.16  14:13
 * @email 453826286@qq.com
 */
@RestController
public class InterfaceErrorController implements ErrorController {


    @RequestMapping(value = "/error")
    public ResultBody handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 500){
            return ResultBody.error(LivExceptionStatus.INTERNAL_SERVER_ERROR);
        }else if(statusCode == 404){
            return ResultBody.error(LivExceptionStatus.NOT_FOUND);
        }else {
            return ResultBody.error(LivExceptionStatus.BAD_REQUEST);
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
