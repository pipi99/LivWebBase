package com.liv.api.base.exception;


import com.liv.api.base.base.ResultBody;
import io.swagger.annotations.Api;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: 处理404接口异常
 * @date 2020.4.16  14:13
 * @email 453826286@qq.com
 */
@RestController("api_base_InterfaceErrorController")
@Api(tags = "APIBASE_404异常")
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
