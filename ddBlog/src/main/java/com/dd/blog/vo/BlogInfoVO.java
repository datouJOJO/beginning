package com.dd.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DD
 * @about 博客信息
 * @date 2022/5/18 16:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "博客信息")
public class BlogInfoVO {

    /**
     * 关于我内容
     */
    @ApiModelProperty(name = "aboutContent", value = "关于我内容", required = true, dataType = "String")
    private String aboutContent;
}
