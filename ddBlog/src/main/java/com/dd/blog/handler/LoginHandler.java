package com.dd.blog.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dd.blog.annotation.GetPlaceInfo;
import com.dd.blog.dto.ConnectionInfo;
import com.dd.blog.utils.IpUtils;
import com.dd.blog.utils.UserThreadLocal;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dd.blog.constant.CommonConst.*;

/**
 * @author DD
 * @about 登录归属地的拦截 登录的时候记录一下ip 登陆地点和
 * @date 2022/5/3 15:27
 */

public class LoginHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            //如果加了要获取用户地址信息的注解
            GetPlaceInfo placeInfo = hm.getMethodAnnotation(GetPlaceInfo.class);
            if(placeInfo != null) {
                // 获取ip
                String ipAddress = IpUtils.getIpAddress(request);
                // 获取访问设备
                UserAgent userAgent = IpUtils.getUserAgent(request);
                String ipSource = IpUtils.getIpSource(ipAddress);
                if (StringUtils.isNotBlank(ipSource)) {
                    ipSource = ipSource.substring(0, 2)
                            .replaceAll(PROVINCE, "")
                            .replaceAll(CITY, "");
                } else {
                    ipSource = UNKNOWN;
                }
                ConnectionInfo connectionInfo = new ConnectionInfo();
                connectionInfo.setIp(ipAddress);
                connectionInfo.setPlace(ipSource);
                UserThreadLocal.put(connectionInfo);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
