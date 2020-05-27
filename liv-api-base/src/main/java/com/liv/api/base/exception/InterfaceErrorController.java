package com.liv.api.base.exception;


import com.alibaba.druid.wall.violation.ErrorCode;
import com.alibaba.fastjson.JSONObject;
import com.liv.api.base.base.ResultBody;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
    private static final String ERROR_PATH="/error";
    private ErrorAttributes errorAttributes;

    @Autowired
    public InterfaceErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes=errorAttributes;
    }

    /**     * web页面错误处理     */
    @RequestMapping(value=ERROR_PATH,produces="text/html")
    @ResponseBody
    public ResultBody errorPageHandler(HttpServletRequest request, HttpServletResponse response) {
        ServletWebRequest requestAttributes =  new ServletWebRequest(request);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        LivCommonException exception = new LivCommonException((int) attr.get("status"),(String)attr.get("error"));
        return  ResultBody.error(exception, attr);
    }

    /**     * 除web页面外的错误处理，比如json/xml等     */
    @RequestMapping(value=ERROR_PATH)
    @ResponseBody
    public ResultBody errorApiHander(HttpServletRequest request) {
        ServletWebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> attr=this.errorAttributes.getErrorAttributes(requestAttributes, false);
        LivCommonException exception = new LivCommonException((int) attr.get("status"),(String)attr.get("error"));
        return  ResultBody.error(exception, attr);
    }

//    @RequestMapping(value = "/error")
//    public ResultBody handleError(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if(statusCode == 500){
//            return ResultBody.error(LivExceptionStatus.INTERNAL_SERVER_ERROR);
//        }else if(statusCode == 404){
//            return ResultBody.error(LivExceptionStatus.NOT_FOUND);
//        }else {
//            return ResultBody.error(LivExceptionStatus.BAD_REQUEST);
//        }
//    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
