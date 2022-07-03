package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.UniqueViewDTO;
import com.dd.blog.vo.UniqueView;

import java.util.List;

/**
 * @author DD
 * @about 用户量统计
 * @date 2022/5/7 14:09
 */
public interface UniqueViewService extends IService<UniqueView> {

    /**
     * 获取7天用户量
     * @return
     */
    List<UniqueViewDTO> listUniqueViews();
}
