package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.ArticleDao;
import com.dd.blog.dao.ArticleTagDao;
import com.dd.blog.dao.CategoryDao;
import com.dd.blog.dao.TagDao;
import com.dd.blog.dto.*;
import com.dd.blog.entity.Article;
import com.dd.blog.entity.ArticleTag;
import com.dd.blog.entity.Category;
import com.dd.blog.entity.Tag;
import com.dd.blog.exception.BizException;
import com.dd.blog.service.ArticleService;
import com.dd.blog.service.ArticleTagService;
import com.dd.blog.service.RedisService;
import com.dd.blog.service.TagService;
import com.dd.blog.strategy.context.SearchStrategyContext;
import com.dd.blog.utils.BeanCopyUtils;
import com.dd.blog.utils.PageUtils;
import com.dd.blog.utils.UserThreadLocal;
import com.dd.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.dd.blog.constant.CommonConst.*;
import static com.dd.blog.constant.RedisPrefixConst.*;
import static com.dd.blog.emuns.ArticleStatusEnum.*;
import static com.dd.blog.constant.RedisPrefixConst.*;

/**
 * 文章服务
 * @author DD
 * @date 2022/4/6 15:35
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private SearchStrategyContext searchStrategyContext;

    @Autowired
    private ArticleTagDao articleTagDao;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public List<ArticleHomeDTO> listHomeArticle() {
        return articleDao.listHomeArticle(PageUtils.getLimitCurrent(), PageUtils.getSize());
    }

    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        //查询推荐文章
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList =  CompletableFuture
                .supplyAsync(() -> articleDao.listRecommendArticles(articleId));
        //查询最新文章
        CompletableFuture<List<ArticleRecommendDTO>> newestArticleList = CompletableFuture
                .supplyAsync(() -> {
                    List<Article> articleList = articleDao.selectList(new LambdaQueryWrapper<Article>()
                            .select(Article::getId, Article:: getArticleTitle, Article::getArticleCover, Article::getCreateTime)
                            .eq(Article::getIsDelete, FALSE)
                            .eq(Article::getStatus, PUBLIC.getStatus())
                            .orderByDesc(Article::getCreateTime)
                            .orderByDesc(Article::getId)
                            .last("limit 5"));
                    return BeanCopyUtils.copyList(articleList, ArticleRecommendDTO.class);
        });
        ArticleDTO article = articleDao.getArticleById(articleId);
        //查询id对应的文章
        if(Objects.isNull(article)) {
            throw new BizException("文章不存在");
        }
        //更新文章浏览量
        updateArticleViewCount(articleId);
        //查询上一篇文章
        Article lastArticle = articleDao.selectOne(new LambdaQueryWrapper<Article>()
            .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .lt(Article::getId, articleId)
                .orderByDesc(Article::getId)
                .last("limit 1"));
        //查询下一篇文章
        Article nextArticle = articleDao.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .gt(Article::getId, articleId)
                .orderByAsc(Article::getId)
                .last("limit 1"));
        article.setLastArticle(BeanCopyUtils.copyObject(lastArticle, ArticlePaginationDTO.class));
        article.setNextArticle(BeanCopyUtils.copyObject(nextArticle, ArticlePaginationDTO.class));
        //封装点赞量和浏览量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if(!Objects.isNull(score)) {
            article.setViewsCount(score.intValue());
        }
        //不知道在哪个位置获取了点赞量
        article.setLikeCount((Integer) redisService.hGet(ARTICLE_LIKE_COUNT, articleId.toString()));
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newestArticleList.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<Article> articlePage = articleDao.selectPage(page, new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getCreateTime)
                .orderByDesc(Article::getCreateTime)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus()));
        List<ArchiveDTO> archiveDTOList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, (int)articlePage.getTotal());
    }

    @Override
    public ArticlePreviewListDTO listArticleByCondition(ConditionVo condition) {
        // 查询文章
        List<ArticlePreviewDTO> articlePreviewDTOList = articleDao.listArticleByCondition(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        // 搜索条件对应名(标签或分类名)
        String name;
        if (Objects.nonNull(condition.getCategoryId())) {
            name = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                    .select(Category::getCategoryName)
                    .eq(Category::getId, condition.getCategoryId()))
                    .getCategoryName();
        } else {
            name = tagService.getOne(new LambdaQueryWrapper<Tag>()
                    .select(Tag::getTagName)
                    .eq(Tag::getId, condition.getTagId()))
                    .getTagName();
        }
        return ArticlePreviewListDTO.builder()
                .articlePreviewDTOList(articlePreviewDTOList)
                .name(name)
                .build();
    }

    @Override
    public List<ArticleSearchDTO> listArticleBySearch(ConditionVo condition) {
        return searchStrategyContext.executeSearchStrategy(condition.getKeywords());
    }

    @Override
    public void saveArticleLike(Integer articleId) {
        String articleLikeKey = ARTICLE_USER_LIKE + UserThreadLocal.get().getUserId()   ;
        if(redisService.sIsMember(articleLikeKey, articleId)) {
            redisService.setRemove(articleLikeKey, articleId);
            redisService.hashDecr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            redisService.setAdd(articleLikeKey, articleId);
            redisService.hashIncr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }

    @Override
    public void saveOrUpdateArticle(ArticleVo articleVo) {
        //保存文章分类
        Category category = saveArticleCategory(articleVo);
        //保存或者修改文章
        Article article = BeanCopyUtils.copyObject(articleVo, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        article.setUserId(UserThreadLocal.get().getUserId());
        this.saveOrUpdate(article);
        saveArticleTag(articleVo, article.getId());
    }

    @Override
    public PageResult<ArticleBackDTO> listArticleBacks(ConditionVo condition) {
        // 查询文章总量
        Integer count = articleDao.countArticleBacks(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台文章
        List<ArticleBackDTO> articleBackDTOList = articleDao.listArticleBacks(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        // 查询文章点赞量和浏览量
        Map<Object, Double> viewsCountMap = redisService.zSetAllScore(ARTICLE_VIEWS_COUNT);
        Map<String, Object> likeCountMap = redisService.hashGetAll(ARTICLE_LIKE_COUNT);
        // 封装点赞量和浏览量
        articleBackDTOList.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
        });
        return new PageResult<>(articleBackDTOList, count);
    }

    @Override
    public ArticleUpdateDTO getArticleBackById(Integer articleId) {
        // 查询文章信息
        Article article = articleDao.selectById(articleId);
        // 查询文章分类
        Category category = categoryDao.selectById(article.getCategoryId());
        String categoryName = null;
        if (Objects.nonNull(category)) {
            categoryName = category.getCategoryName();
        }
        // 查询文章标签
        List<String> tagNameList = tagDao.listTagNameByArticleId(articleId);
        // 封装数据
        ArticleUpdateDTO articleVO = BeanCopyUtils.copyObject(article, ArticleUpdateDTO.class);
        articleVO.setCategoryName(categoryName);
        articleVO.setTagNameList(tagNameList);
        return articleVO;
    }

    @Override
    public void updateArticleTop(ArticleTopVO articleTopVO) {
        // 修改文章置顶状态
        Article article = Article.builder()
                .id(articleTopVO.getId())
                .isTop(articleTopVO.getIsTop())
                .build();
        articleDao.updateById(article);
    }

    @Override
    public void updateArticleDelete(DeleteVO deleteVO) {
        // 修改文章逻辑删除状态
        List<Article> articleList = deleteVO.getIdList().stream()
                .map(id -> Article.builder()
                        .id(id)
                        .isTop(FALSE)
                        .isDelete(deleteVO.getIsDelete())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(articleList);
    }

    @Override
    public void deleteArticles(List<Integer> articleIdList) {
        // 删除文章标签关联
        articleTagDao.delete(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getArticleId, articleIdList));
        // 删除文章
        articleDao.deleteBatchIds(articleIdList);
    }

    private void saveArticleTag(ArticleVo articleVO, Integer id) {
        // 编辑文章则删除文章所有标签
// 编辑文章则删除文章所有标签
        if (Objects.nonNull(articleVO.getId())) {
            articleTagDao.delete(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId, articleVO.getId()));
        }
        // 添加文章标签
        List<String> tagNameList = articleVO.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 查询已存在的标签
            List<Tag> existTagList = tagService.list(new LambdaQueryWrapper<Tag>()
                    .in(Tag::getTagName, tagNameList));
            List<String> existTagNameList = existTagList.stream()
                    .map(Tag::getTagName)
                    .collect(Collectors.toList());
            List<Integer> existTagIdList = existTagList.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            // 对比新增不存在的标签
            tagNameList.removeAll(existTagNameList);
            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream().map(item -> Tag.builder()
                        .tagName(item)
                        .build())
                        .collect(Collectors.toList());
                tagService.saveBatch(tagList);
                List<Integer> tagIdList = tagList.stream()
                        .map(Tag::getId)
                        .collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }
            // 提取标签id绑定文章
            List<ArticleTag> articleTagList = existTagIdList.stream().map(item -> ArticleTag.builder()
                    .articleId(id)
                    .tagId(item)
                    .build())
                    .collect(Collectors.toList());
            articleTagService.saveBatch(articleTagList);
        }
    }

    private Category saveArticleCategory(ArticleVo articleVo) {
        //判断分类是否存在
        Category category = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                .eq(Category::getCategoryName, articleVo.getCategoryName()));
        if(Objects.isNull(category)) {
            category = Category.builder()
                    .categoryName(articleVo.getCategoryName())
                    .build();
            categoryDao.insert(category);
        }
        return category;
    }

    /**
     * 更新文章浏览量
     * @param articleId
     * @return
     */
    public void updateArticleViewCount(Integer articleId) {
        Set<Integer> articleSet = (Set<Integer>) Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElse(new HashSet<>());
        //该文章还没有被访问过
        if(!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            //浏览量加一
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }
}
