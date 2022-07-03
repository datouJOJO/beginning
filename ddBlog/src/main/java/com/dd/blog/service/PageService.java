package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.entity.Page;
import com.dd.blog.vo.PageVO;

import java.util.List;

/**
 * 页面服务
 * @author DD
 * @date 2022/4/5 17:58
 */

public interface PageService extends IService<Page> {

    /**
     * 获取页面列表
     * @return {@link List<PageVO>}
     */
    List<PageVO> listPages();

    void saveOrUpdatePage(PageVO pageVO);

    void deletePage(Integer pageId);
}
