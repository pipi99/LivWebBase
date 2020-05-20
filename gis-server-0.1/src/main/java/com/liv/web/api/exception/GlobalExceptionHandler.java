package com.liv.web.api.exception;

import com.liv.web.api.base.CommonEnum;
import com.liv.web.api.base.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.exception
 * @Description: Controller 全局异常处理
 * @date 2020.3.26  10:54
 * @email 453826286@qq.com
 */
@RestControllerAdvice(annotations= RestController.class)
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理空指针的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    public ResultBody exceptionHandler( NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return ResultBody.error(CommonEnum.NULL_POINT);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    @ResponseStatus(value=HttpStatus.BAD_REQUEST,reason = "非法参数")
    public ResultBody IllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数异常！原因是:",e);
        return ResultBody.error(CommonEnum.BAD_REQUEST);
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultBody exceptionHandler( Exception e){
        log.error("发生异常！原因是:",e);
        return ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }
}
