package com.dd.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dd.blog.dao.*;
import com.dd.blog.dto.*;
import com.dd.blog.entity.Article;
import com.dd.blog.entity.WebsiteConfig;
import com.dd.blog.service.BlogInfoService;
import com.dd.blog.service.PageService;
import com.dd.blog.service.RedisService;
import com.dd.blog.service.UniqueViewService;
import com.dd.blog.utils.BeanCopyUtils;
import com.dd.blog.utils.IpUtils;
import com.dd.blog.vo.BlogInfoVO;
import com.dd.blog.vo.PageVO;
import com.dd.blog.vo.WebsiteConfigVO;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.dd.blog.emuns.ArticleStatusEnum.*;
import static com.dd.blog.constant.CommonConst.*;
import static com.dd.blog.constant.RedisPrefixConst.*;

/**
 * 博客信息服务
 * @author DD
 * @date 2022/4/5 0:45
 */

@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PageService pageService;
    @Autowired
    private WebsiteConfigDao websiteConfigDao;
    @Autowired
    private UniqueViewService uniqueViewService;
    @Override
    public BlogHomeInfoDTO getBlogHomeInfo() {
        //获取文章数量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
        .eq(Article::getStatus, PUBLIC.getStatus())
        .eq(Article::getIsDelete, FALSE));
        //获取标签数量
        Integer tagCount = tagDao.selectCount(new LambdaQueryWrapper<>());
        //获取分类数量
        Integer categoryCount = categoryDao.selectCount(new LambdaQueryWrapper<>());
        //查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
//        System.out.println("==============================================");
//        System.out.println(count);
//        System.out.println("==============================================");
        String viewsCount;
        if(count == null) {
            redisService.set(BLOG_VIEWS_COUNT, 0);
            viewsCount = "0";
//            System.out.println("key不存在！！！！！！！！！！");
        } else {
            viewsCount = count.toString();
//            System.out.println("key存在！！！！！！！！！！");
        }
//        System.out.println("==============================================");
//        System.out.println(viewsCount);
//        System.out.println("==============================================");
        //获取网站配置
        WebsiteConfigVO websiteConfig = this.getWebsiteConfig();
        List<PageVO> pageVOList = pageService.listPages();
        //封装数据
        return BlogHomeInfoDTO.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewsCount(viewsCount)
                .websiteConfig(websiteConfig)
                .pageList(pageVOList)
                .build();
    }

    @Resource
    private HttpServletRequest request;

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if(Objects.nonNull(websiteConfig)) {
            websiteConfigVO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            String config = websiteConfigDao.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVO = JSON.parseObject(config, WebsiteConfigVO.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVO;
    }

    @Override
    public AdminDTO getAdminInfo() {
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        //博客浏览量
        Integer viewCount = Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        //查询留言量
        Integer messageCount = messageDao.selectCount(null);
        //查询用户量
        Integer userCount = userInfoDao.selectCount(null);
        //查询文章量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE));
        //查询一周用户量
        List<UniqueViewDTO> uniqueViewDTOList = uniqueViewService.listUniqueViews();
        //查询文章统计
        List<ArticleStatisticsDTO> articleStatisticsDTOList = articleDao.listArticleStatistics();
        //查询分类数据
        List<CategoryDTO> categoryDTOList = categoryDao.listCategoryDTO();
        //列出标签数据
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagDao.selectList(null), TagDTO.class);
        //查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisService.zSetReversRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);
        AdminDTO adminDTO = AdminDTO.builder()
                .articleStatisticsList(articleStatisticsDTOList)
                .tagDTOList(tagDTOList)
                .viewsCount(viewCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOList(categoryDTOList)
                .uniqueViewDTOList(uniqueViewDTOList)
                .build();
        if(CollectionUtils.isNotEmpty(articleMap)) {
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            adminDTO.setArticleRankDTOList(articleRankDTOList);
        }
        return adminDTO;
    }

    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        // 修改网站配置
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(1)
                .config(JSON.toJSONString(websiteConfigVO))
                .build();
        websiteConfigDao.updateById(websiteConfig);
        // 删除缓存
        redisService.del(WEBSITE_CONFIG);
    }

    @Override
    public String getAbout() {
        Object value = redisService.get(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Override
    public void updateAbout(BlogInfoVO blogInfoVO) {
        redisService.set(ABOUT, blogInfoVO.getAboutContent());
    }

    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        List<Integer> articleIdList = new ArrayList<>();
        articleMap.forEach((key, value) -> articleIdList.add((Integer)key));
        return articleDao.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle)
                .in(Article::getId, articleIdList))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount))
                .collect(Collectors.toList());
    }
}
