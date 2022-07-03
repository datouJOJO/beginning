package com.dd.blog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.dd.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * @author DD
 * @about JWT工具类
 * @date 2022/5/2 21:47
 */
public class JWTUtils {
    //密钥
    private static final String SALT = "DaTouIsAHandsomeBoy";
    /**
     * 生成token head.payload.signature
     * @return
     */
    public static String getToken(Integer userId) {
        Calendar instance = Calendar.getInstance();
        //token有效时间 30分钟
        instance.add(Calendar.SECOND, 30 * 60);
//        instance.add(Calendar.SECOND, 10);
        String token = JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SALT));
        return token;
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            //设置签名的加密算法：HMAC256
            Algorithm algorithm = Algorithm.HMAC256(SALT);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static Integer getId(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SALT);
        JWTVerifier verifier = JWT.require(algorithm).build();
        if (verify(token)) {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asInt();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
//        String token = JWTUtils.getToken(122);
//        System.out.println(getId(token));
        System.out.println(getId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTE2NzM5MDB9.fiPTmcrviaTW0yOATjh8lGaHtdbeHImVMNTSckcWEC8"));
//        String token = getToken("1");
//        System.out.println(token);
//        System.out.println(verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTE2NTA0NzksInVzZXJJZCI6IjEifQ.m18HFnQjJlwDWy3xMNy_0uobctL5cKB3-1URQ_hAd_w"));
    }
}
