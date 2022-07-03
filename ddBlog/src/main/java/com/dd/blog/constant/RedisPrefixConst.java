package com.dd.blog.constant;

/**
 * @author DD
 * @date 2022/4/5 10:50
 */
public class RedisPrefixConst {

    /**
     * 博客浏览量
     */
    public static final String BLOG_VIEWS_COUNT = "blog_view_count";
    /**
     * 页面封面
     */
    public static final String PAGE_COVER = "page_cover";
    /**
     * 网站配置
     */
    public static final String WEBSITE_CONFIG = "website_config";
    /**
     * 文章浏览量
     */
    public static final String ARTICLE_VIEWS_COUNT = "articleViewCount";
    /**
     * 文章点赞量
     */
    public static final String ARTICLE_LIKE_COUNT = "articleLikeCount";
    /**
     * 访客
     */
    public static final String UNIQUE_VISITOR = "unique_visitor";
    /**
     * 用户地区
     */
    public static final String USER_AREA = "user_area";
    /**
     * 访客地区
     */
    public static final String VISITOR_AREA = "visitor_area";
    /**
     * 验证码
     */
    public static final String USER_CODE_KEY = "code:";
    /**
     * 验证码过期时间
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60;
    /**
     * 评论点赞数
     */
    public static final String COMMENT_LIKE_COUNT = "commentLikeCount";
    /**
     * 用户点赞评论列表
     */
    public static final String COMMENT_USER_LIKE = "commentUserLike";
    /**
     * 用户点赞文章列表
     */
    public static final String ARTICLE_USER_LIKE = "articleUserLike";
    /**
     * 在线用户tokens
     */
    public static final String ONLINE_USERS = "onlineUsers";
    /**
     * 关于我信息
     */
    public static final String ABOUT = "about";
}
