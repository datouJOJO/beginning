package com.dd.blog.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dd.blog.annotation.IsLogin;
import com.dd.blog.dto.ConnectionInfo;
import com.dd.blog.service.RedisService;
import com.dd.blog.utils.JWTUtils;
import com.dd.blog.utils.UserCounter;
import com.dd.blog.utils.UserThreadLocal;
import com.dd.blog.vo.Result;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.dd.blog.emuns.StatusCodeEnum.*;
import static com.dd.blog.constant.CommonConst.*;
import static com.dd.blog.constant.RedisPrefixConst.*;
/**
 * @author DD
 * @about token拦截器
 * @date 2022/5/3 23:37
 */

public class TokenHandler implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            IsLogin isLogin = hm.getMethodAnnotation(IsLogin.class);
            if(isLogin != null) {
                //取出token
                String token = request.getHeader("Authorization");
                //是否为空
                if(token == null || StringUtils.isBlank(token)) {
//                    System.out.println("====================" + hm.getMethod().getName());
                    if(!hm.getMethod().getName().equals("saveMessage")) {
                        response.setContentType(APPLICATION_JSON);
                        response.getWriter().write(JSON.toJSONString(Result.fail(TOKEN_IS_BLANK.getCode(), TOKEN_IS_BLANK.getMessage())));
                        return false;
                    } else {
                        return true;
                    }
                }
                //注销直接删除信息就行
                if(hm.getMethod().getName().equals("logout")) {
                    //直接把token删除
                    redisService.setRemove(ONLINE_USERS, token);
                    return true;
                }
                //token是否过期
                DecodedJWT jwt = JWT.decode(token);
                if(jwt.getExpiresAt().before(new Date())) {
                    //redis删除在线用户
                    redisService.setRemove(ONLINE_USERS, token);
//                    System.out.println("token is expired");
                    //返回一个错误信息
                    response.setContentType(APPLICATION_JSON);
                    response.getWriter().write(JSON.toJSONString(Result.fail(TOKEN_FAIL.getCode(), TOKEN_FAIL.getMessage())));
                    //如果token过期了
                    //就把当前user的id从保存id set移除
//                    counter.remove(token);
                    return false;
                }
                //校验token
                if(JWTUtils.verify(token)) {
                    ConnectionInfo connectionInfo = new ConnectionInfo();
//                    System.out.println("-----------------------id:" + id);
                    //解析token拿出id
                    Integer id = JWTUtils.getId(token);
                    connectionInfo.setUserId(id);
                    connectionInfo.setToken(token);
                    UserThreadLocal.put(connectionInfo);
                    return true;
                }
                response.setContentType(APPLICATION_JSON);
                response.getWriter().write(JSON.toJSONString(Result.fail(TOKEN_CHECK_FAIL.getCode(), TOKEN_CHECK_FAIL.getMessage())));
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //删除用户数据
        UserThreadLocal.remove();
    }

    public static void main(String[] args) {
//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.SECOND, 5);
//        String token = JWT.create()
//                .withClaim("userId", 1)
//                .withExpiresAt(instance.getTime())
//                .sign(Algorithm.HMAC256("SALT"));
//        //token是否过期
//        System.out.println(token);
//        DecodedJWT jwt = JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTE1OTMxNTAsInVzZXJJZCI6MX0.tWbxopan73mqopK3F-O0hLEsfytieLYxFvcZhiHu1S4");
//
//        if( jwt.getExpiresAt().before(new Date())) {
//            System.out.println("token is expired");
//        }
//        //校验token
//        System.out.println(JWTUtils.verify(token));
    }
}
