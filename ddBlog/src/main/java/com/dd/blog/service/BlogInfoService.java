package com.dd.blog.service;

import com.dd.blog.dto.AdminDTO;
import com.dd.blog.dto.BlogHomeInfoDTO;
import com.dd.blog.vo.BlogInfoVO;
import com.dd.blog.vo.WebsiteConfigVO;

/**
 * 博客信息服务
 * @author DD
 * @date 2022/4/5 0:26
 */

public interface BlogInfoService {

    /**
     * 获取首页信息
     * @return
     */
    BlogHomeInfoDTO getBlogHomeInfo();

    /**
     * 上传访客信息
     */
    void report();

    /**
     * 获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();

    AdminDTO getAdminInfo();

    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);

    String getAbout();

    void updateAbout(BlogInfoVO blogInfoVO);
}
