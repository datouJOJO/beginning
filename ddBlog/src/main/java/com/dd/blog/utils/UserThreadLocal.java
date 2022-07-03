package com.dd.blog.utils;

import com.dd.blog.dto.ConnectionInfo;
import org.springframework.stereotype.Component;

/**
 * @author DD
 * @about
 * @date 2022/5/3 15:31
 */


public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<ConnectionInfo> LOCAL = new ThreadLocal<>();

    public static void put(ConnectionInfo info){
        LOCAL.set(info);
    }
    public static ConnectionInfo get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
