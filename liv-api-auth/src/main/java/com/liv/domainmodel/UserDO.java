package com.liv.domainmodel;

import com.liv.viewmodel.UserVO;
import lombok.Data;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.domainmodel
 * @Description: 用户模型
 * @date 2020.5.13  14:59
 * @email 453826286@qq.com
 */
@Data
public class UserDO extends SimpleAuthorizationInfo {
    /**用户信息*/
    private UserVO user;
}
