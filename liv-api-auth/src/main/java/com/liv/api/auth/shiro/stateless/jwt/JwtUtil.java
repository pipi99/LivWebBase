package com.liv.api.auth.shiro.stateless.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liv.api.auth.shiro.cache.CacheFactory;
import com.liv.api.auth.shiro.stateless.TokenHandle;
import com.liv.api.auth.utils.AppConst;
import com.liv.api.base.utils.LivContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import java.util.UUID;

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

    private static Cache<String, Object> subjectCache;
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

        String jwtId = getJwtIdByToken(token);
        if(!token.equals(CacheFactory.getLoginSuccessSubjectCache().get(jwtId) )){
            return false;
        }

        boolean result = false;
        try{
            String secret = getClaim(token, AppConst.ACCOUNT) + getJwtUtil().jwtProperties.getSecretKey();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                .withClaim(AppConst.ACCOUNT, getAccountByToken(token))
                .withClaim("jwt-id", jwtId)
                .acceptExpiresAt(System.currentTimeMillis() + getJwtUtil().jwtProperties.getTokenExpireTime()*60*1000)  //JWT 正确的配置续期姿势
                .build();
            verifier.verify(token);

            // Redis缓存JWT续期
            CacheFactory.getLoginSuccessSubjectCache().put(jwtId,token);

            /****每次必须绑定登录用户到线程中，否则无法控制权限***/
            PrincipalCollection principalCollection = (PrincipalCollection)CacheFactory.getLoginSuccessSubjectCache().get(token);
            Subject subject = new Subject.Builder().principals(principalCollection).authenticated(true).buildSubject();
            ThreadContext.bind(subject);
            CacheFactory.getLoginSuccessSubjectCache().put(token,principalCollection);

            result = true;
        }catch(Exception e){
            result = false;
        }

        return result;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 18:23
     * @Description: 当前用绑定线程
     **/
//    private static boolean bindSubject(String  jwttoken){
//        /****每次必须绑定登录用户到线程中，否则无法控制权限***/
//        PrincipalCollection principalCollection = (PrincipalCollection)CacheFactory.getLoginSuccessSubjectCache().get(jwttoken);
//
//        if (principalCollection != null) {
//            Subject subject = new Subject.Builder().principals(principalCollection).authenticated(principalCollection!=null).buildSubject();
//            ThreadContext.bind(subject);
//        }
//        return principalCollection!=null;
//    }

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
     * @return
     */
    public static String sign() {
        Subject user = SecurityUtils.getSubject();
        String account = (String)user.getPrincipals().getPrimaryPrincipal();

        String jwtId = UUID.randomUUID().toString();                 //JWT 随机ID,做为验证的key
        // 帐号加JWT私钥加密
        String secret = account + getJwtUtil().jwtProperties.getSecretKey();
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + getJwtUtil().jwtProperties.getTokenExpireTime()*60*1000);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token =  JWT.create()
                .withClaim(AppConst.ACCOUNT, account)
                .withClaim("jwt-id", jwtId)
                .withExpiresAt(date)
                .sign(algorithm);

        //缓存用户登录信息
        CacheFactory.getLoginSuccessSubjectCache().put(jwtId,token);
        CacheFactory.getLoginSuccessSubjectCache().put(token,user.getPrincipals());
        return token;
    }

    /**
     * @Author: LiV
     * @Date: 2020.4.22 16:30
     * @Description: cookeie和header中存储token
     **/
    public static void cookieHeaderToken(HttpServletResponse response,String  jwttoken) {
        //Cookie存储token
        Cookie cookie = new Cookie(LivContextUtils.REQUEST_AUTH_HEADER, jwttoken);
        //值大于0, 将cookie存储于本地磁盘, 过期后删除；值小于0, cookie不会保存于本地, 浏览器会话结束后, 将会删除
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(true);
//        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);

        //header
        response.setHeader("Access-Control-Expose-Headers", LivContextUtils.REQUEST_AUTH_HEADER);
        response.setHeader(LivContextUtils.REQUEST_AUTH_HEADER, jwttoken);
    }

    /**
     * 根据Token获取 count
     */
    public static String getAccountByToken(String token)  {
        return JWT.decode(token).getClaim(AppConst.ACCOUNT).asString();
    }

    /**
     * 根据Token 获取jwt-id
     */
    public static String getJwtIdByToken(String token)  {
        return JWT.decode(token).getClaim("jwt-id").asString();
    }

}