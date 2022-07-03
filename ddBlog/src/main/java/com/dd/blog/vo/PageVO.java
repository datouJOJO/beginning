package com.dd.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 页面信息
 * @author DD
 * @date 2022/4/5 0:40
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "页面信息")
public class PageVO {

    /**
     * 页面id
     */
    @ApiModelProperty(name = "id", value = "页面id", required = true, dataType = "Integer")
    private Integer id;
    /**
     * 页面名称
     */
    @NotBlank(message = "页面名称不为空")
    @ApiModelProperty(name = "pageName", value = "页面名称", required = true, dataType = "String")
    private String pageName;
    /**
     * 页面标签
     */
    @NotBlank(message = "页面标签不为空")
    @ApiModelProperty(name = "pageLabel", value = "页面标签", required = true, dataType = "String")
    private String pageLabel;
    /**
     * 页面封面
     */
    @NotBlank(message = "页面封面不为空")
    @ApiModelProperty(name = "pageCover", value = "页面封面", required = true, dataType = "String")
    private String pageCover;
}
