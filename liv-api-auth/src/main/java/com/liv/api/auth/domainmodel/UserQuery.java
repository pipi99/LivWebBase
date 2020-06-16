package com.liv.api.auth.domainmodel;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liv.api.auth.dao.datamodel.User;
import com.liv.api.base.base.BaseBean;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.api.auth.domainmodel
 * @Description: 用户查询类
 * @date 2020.6.9  10:11
 * @email 453826286@qq.com
 */
@Data
public class UserQuery extends BaseBean<User> {
    /**
     * 主键
     *
     * Nullable:  false
     */
    private Long userId;

    /**
     * 用户名称
     *
     * Nullable:  true
     */
    private String userName;

    /**
     * 组织机构
     *
     * Nullable:  true
     */
    private Long orgId;

    /**
     * 姓名
     *
     * Nullable:  true
     */
    private String alias;

    /**
     * 手机
     *
     * Nullable:  true
     */
    private Integer mobile;

    /**
     * 邮箱
     *
     * Nullable:  true
     */
    private String email;

    /**
     * 性别
     *
     * Nullable:  true
     */
    private String gender;

    /**
     * 学历
     *
     * Nullable:  true
     */
    private String degree;

    /**
     * 出生年月日
     *
     * Nullable:  true
     */
    private Date birthday;

    /**
     * 照片
     *
     * Nullable:  true
     */
    private String photo;

    /**
     * 创建日期
     *
     * Nullable:  true
     */
    private Date createDate;

    /**
     * @Author: LiV
     * @Date: 2020.4.19 20:42
     * @Description: 是否锁定  1是 0否
     **/
    private String locked;

    /**
     * 锁定时间
     *
     * Nullable:  true
     */
    private Date locktime;

    @Override
    public QueryWrapper<User> getQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        if(StringUtils.isNotEmpty(this.alias)){
            qw.like("ALIAS",this.alias);
        }

        return qw;
    }
}
