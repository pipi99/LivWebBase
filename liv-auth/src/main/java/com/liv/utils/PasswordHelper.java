package com.liv.utils;

import com.liv.dao.datamodel.User;
import com.liv.shiro.realms.RetryLimitHashedCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author LiV
 * @Title:
 * @Package com.liv.utils
 * @Description: 用户密码处理工具
 * @date 2020.4.19  16:21
 * @email 453826286@qq.com
 */
public class PasswordHelper {
    private static String algorithmName = "md5";
    private static final int hashIterations = 2;
    /**
     * @Author: LiV
     * @Date: 2020.4.19 17:08
     * @Description: 用户密码加密
     **/
    public static void encryptNewUserPassword(User user) {
        user.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.19 17:08
     * @Description: 用户密码加密
     **/
    public static CredentialsMatcher getMatcher(EhCacheManager ehCacheManager) {
        //这里的规则需要与  passwordHelper中一致
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager);
        matcher.setHashAlgorithmName(algorithmName);//不设置算法名称将报错
        //是Hex编码的还是Base64编码的。对应SimpleHash的toHex()和toBase64()
        matcher.setStoredCredentialsHexEncoded(true);
        matcher.setHashIterations(hashIterations);//默认迭代次数为1
        return matcher;
    }
}
