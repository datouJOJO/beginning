package com.dd.blog.utils;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author DD
 * @about 在线用户统计机器
 * @date 2022/5/9 16:19
 */

//单例注入
@Component
public class UserCounter {

    private Set<String> userTokens = new HashSet<>();

    public void insertId(String token) {
        userTokens.add(token);
    }

    public Set<String> getOnlineUsers() {
        return userTokens;
    }

    public void remove(String token) {
        if(token != null && userTokens.size() > 0) {
            userTokens.remove(token);
        }
    }
}
