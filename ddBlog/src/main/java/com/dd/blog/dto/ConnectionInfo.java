package com.dd.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DD
 * @about
 * @date 2022/5/3 15:32
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionInfo {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * ip
     */
    private String ip;
    /**
     * 归属地
     */
    private String place;
    /**
     * 用户token
     */
    private String token;
}
