package com.liv.web.api.base.exception;

import com.google.common.collect.Lists;
import com.liv.web.api.base.base.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: 全局异常处理
 * @date 2020.3.26  10:54
 * @email 453826286@qq.com
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @Description: 参数校验异常
     **/
    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResultBody ConstraintViolationException(ConstraintViolationException e) {
        log.error("参数校验异常！原因是:",e);
        List<String> msg = Lists.newArrayList();
        if(e instanceof ConstraintViolationException){
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                msg.add(item.getMessage());
            }
        }
        return ResultBody.error(LivExceptionStatus.VALIDATE_FAIL,msg);
    }

    /**
     * 处理Model数据校验的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ValidateException.class)
    public ResultBody ValidateException(HttpServletRequest req, ValidateException e){
        List<String> msg = (List)req.getAttribute("msg");
        ResultBody rb = ResultBody.error(LivExceptionStatus.VALIDATE_FAIL,msg);
        log.error("发生异常！原因是:"+rb);
        return rb;
    }

    // 捕捉shiro的异常
    @ExceptionHandler(value = { UnauthenticatedException.class ,UnauthorizedException.class,ShiroException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultBody handle401(Exception e,HttpServletRequest request) {
        log.error("发生异常！原因是:",e);
        return ResultBody.error(LivExceptionStatus.UNAUTHORIZED, "您没有访问【"+request.getRequestURI()+"】的权限");
    }

    /**
     * 处理其他异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultBody globalException(HttpServletRequest request, Throwable ex) {
        log.error("发生异常！原因是:",ex);
        return  ResultBody.error(getStatus(request).value(),ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
