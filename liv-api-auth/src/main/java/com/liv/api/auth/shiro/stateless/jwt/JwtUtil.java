package com.liv.api.auth.shiro.stateless.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liv.api.auth.shiro.cache.CacheFactory;
import com.liv.api.auth.utils.AppConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
/**
 * @author LiV
 * @Title:
 * @Package com.liv.shiro.stateless
 * @Description: JwtUtil 工具类
 * @date 2020.4.21  13:58
 * @email 453826286@qq.com
 */

@Component
public class JwtUtil {

    @Autowired
    JwtProperties jwtProperties;

    private static Cache<String, PrincipalCollection> subjectCache;
    private static JwtUtil jwtUtil;

    @PostConstruct
    public void init() {
        jwtUtil = this;
        jwtUtil.jwtProperties = this.jwtProperties;
    }

    private static JwtUtil getJwtUtil(){
        if(jwtUtil.subjectCache == null){
            jwtUtil.subjectCache = CacheFactory.getLoginSuccessSubjectCache();
        }
        return jwtUtil;
    }

    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        if(StringUtils.isEmpty(token)){
            return false;
        }
        boolean result = false;
        try{
            String secret = getClaim(token, AppConst.ACCOUNT) + getJwtUtil().jwtProperties.secretKey;
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            result = true;
        }catch(Exception e){

        }
        return result&&bindSubject(token);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 18:23
     * @Description: 当前用绑定线程
     **/
    private static boolean bindSubject(String  jwttoken){
        /****每次必须绑定登录用户到线程中，否则无法控制权限***/
        PrincipalCollection principalCollection = subjectCache.get(jwttoken);
        if (principalCollection != null) {
            Subject subject = new Subject.Builder().principals(principalCollection).authenticated(principalCollection!=null).buildSubject();
            ThreadContext.bind(subject);
        }
        return principalCollection!=null;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     * @param account
     * @return
     */
    public static String sign(String account) {
        // 帐号加JWT私钥加密
        String secret = account + getJwtUtil().jwtProperties.getSecretKey();
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + getJwtUtil().jwtProperties.getTokenExpireTime()*60*1000l);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim(AppConst.ACCOUNT, account)
                .withClaim(AppConst.CURRENT_TIME_MILLIS, String.valueOf(System.currentTimeMillis()))
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 续签
     * @param token
     * @return
     */
    private static String renewal(String token) {
        String account = getClaim(token,AppConst.ACCOUNT);
        // 帐号加JWT私钥加密
        String secret = account + getJwtUtil().jwtProperties.getSecretKey();
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + getJwtUtil().jwtProperties.getTokenExpireTime()*60*1000l);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim(AppConst.ACCOUNT, account)
                .withClaim(AppConst.CURRENT_TIME_MILLIS,  String.valueOf(System.currentTimeMillis()))
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 18:23
     * @Description: token处理
     **/
    public static String tokenStore(HttpServletResponse response,String  jwttoken,boolean renewal){
        jwttoken = renewal?renewal(jwttoken):jwttoken;
        if(renewal){
            jwttoken = renewal(jwttoken);
        }
        //重新生成token
        cookieHeaderToken( response,jwttoken);
        return jwttoken;
    }


    /**
     * @Author: LiV
     * @Date: 2020.4.22 16:30
     * @Description: cookeie和header中存储token
     **/
    public static void cookieHeaderToken(HttpServletResponse response,String  jwttoken){
        //Cookie存储token
        Cookie cookie = new Cookie(AppConst.REQUEST_AUTH_HEADER, jwttoken);
        //值大于0, 将cookie存储于本地磁盘, 过期后删除；值小于0, cookie不会保存于本地, 浏览器会话结束后, 将会删除
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(true);
//        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);

        //header
        response.setHeader("Access-Control-Expose-Headers", AppConst.REQUEST_AUTH_HEADER);
        response.setHeader(AppConst.REQUEST_AUTH_HEADER, jwttoken);
    }



}