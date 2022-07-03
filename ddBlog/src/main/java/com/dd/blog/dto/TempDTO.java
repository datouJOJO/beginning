package com.dd.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DD
 * @about 临时对象
 * @date 2022/5/3 0:05
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempDTO {
    /**
     * 方法执行标志
     */
    public Boolean flag;
    /**
     * 返回的数据
     */
    public String data;
}
