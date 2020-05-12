package com.liv.web.api.base.base;

import com.google.common.collect.Lists;
import com.liv.web.api.base.exception.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.common
 * @Description: controller 父类
 * @date 2020.3.26  10:02
 * @email 453826286@qq.com
 */
public abstract class BaseController<M extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T>, T,TService> {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BaseService<M,T> _b_s;

    protected TService service;

    @PostConstruct
    private void service(){
        this.service = (TService)this._b_s;
    }
    /**
     * 获取request对象
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取Response对象
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取Session对象
     */
    protected HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.16 19:31
     * @Description: 判断参数校验结果
     **/
    protected void validateResult(BindingResult result){

        if(result.hasErrors()){
            List<String> msg = Lists.newArrayList();
            for (ObjectError error : result.getAllErrors()) {
                msg.add(error.getDefaultMessage());
            }
            getRequest().setAttribute("msg",msg);
            //交给系统处理异常
            throw new ValidateException();
        }
    }
}
