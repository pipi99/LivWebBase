package com.liv.api.base.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.web.api.utils
 * @Description: 日期工具类
 * @date 2020.4.28  17:25
 * @email 453826286@qq.com
 */
public class LivDateUtils {

    /**
     * @Author: LiV
     * @Date: 2020.4.28 17:26
     * @Description: 获取当前时间
     **/
    public static Calendar getCal(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); //格式差8小时
        return calendar;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.28 17:26
     * @Description: 获取当前时间
     **/
    public static Date getCurDate(){
        Calendar calendar = getCal();
        return calendar.getTime();
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.28 17:26
     * @Description: 获取当前时间
     **/
    public static Date getCurDateAfterOrBefore(int field,int val){
        Calendar calendar =  getCal();
        calendar.add(field,val);
        return calendar.getTime();
    }
}
