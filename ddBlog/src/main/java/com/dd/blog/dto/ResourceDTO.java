package com.dd.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author DD
 * @about 资源dto
 * @date 2022/5/18 16:03
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {

    /**
     * 权限id
     */
    private Integer id;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * 权限路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 是否禁用
     */
    private Integer isDisable;

    /**
     * 是否匿名访问
     */
    private Integer isAnonymous;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 权限列表
     */
    private List<ResourceDTO> children;
}
