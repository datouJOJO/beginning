package com.dd.blog.controller;

import com.dd.blog.annotation.GetPlaceInfo;
import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dto.ArticleBackDTO;
import com.dd.blog.dto.ArticleUpdateDTO;
import com.dd.blog.service.ArticleService;
import com.dd.blog.utils.QiniuUtils;
import com.dd.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import static com.dd.blog.constant.OptTypeConst.*;
import static com.dd.blog.emuns.StatusCodeEnum.UPLOAD_FAIL;
import static com.dd.blog.constant.OptTypeConst.*;
/**
 * 文章控制器
 * @author DD
 * @date 2022/4/6 15:29
 */

@Api(tags = "文章模块")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "查看首页文章列表")
    @GetMapping("articles")
    public <T>Result listArticles() {
        return Result.ok(articleService.listHomeArticle());
    }

    @GetMapping("/articles/{articleId}")
    @ApiOperation(value = "根据文章id获取文章详情")
    public <T> Result getArticleById(@PathVariable("articleId")Integer articleId) {
        return Result.ok(articleService.getArticleById(articleId));
    }

    @GetMapping("/articles/archives")
    @ApiOperation(value = "获取文章归档")
    public <T> Result listArchives() {
        return Result.ok(articleService.listArchives());
    }

    @GetMapping("/articles/condition")
    @ApiOperation(value = "根据条件列出文章列表")
    public <T> Result listArticleByCondition(ConditionVo condition) {
        return Result.ok(articleService.listArticleByCondition(condition));
    }
    @GetMapping("/articles/search")
    public <T> Result listArticleBySearch(ConditionVo condition) {
        return Result.ok(articleService.listArticleBySearch(condition));
    }

    @PostMapping("/articles/{articleId}/like")
    @ApiOperation(value = "文章点赞")
    @IsLogin
    @OptLog(optType = SAVE_OR_UPDATE)
    public <T>Result saveArticleLike(@PathVariable("articleId") Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.ok();
    }

    @Autowired
    public QiniuUtils qiniuUtils;

    @PostMapping("/admin/articles/images")
    @ApiOperation(value = "上传文章图片")
    @IsLogin
    public <T>Result saveArticleImages(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            String url = QiniuUtils.url + fileName;
            return Result.ok(url);
        }
        return Result.fail(UPLOAD_FAIL.getMessage());
    }

    @PostMapping("/admin/articles")
    @ApiOperation(value = "保存或者更新文章")
    @IsLogin
    @GetPlaceInfo
    @OptLog(optType = SAVE_OR_UPDATE)
    public <T>Result saveOrUpdateArticle(@RequestBody ArticleVo articleVo) {
        articleService.saveOrUpdateArticle(articleVo);
        return Result.ok();
    }

    @ApiOperation(value = "查看后台文章")
    @GetMapping("/admin/articles")
    @IsLogin
    public Result<PageResult<ArticleBackDTO>> listArticleBacks(ConditionVo condition) {
        return Result.ok(articleService.listArticleBacks(condition));
    }

    @ApiOperation(value = "根据id查看后台文章")
    @GetMapping("/admin/articles/{articleId}")
    @IsLogin
    public Result<ArticleUpdateDTO> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleBackById(articleId));
    }

    @OptLog(optType = UPDATE)
    @IsLogin
    @ApiOperation(value = "修改文章置顶")
    @PutMapping("/admin/articles/top")
    public Result<?> updateArticleTop(@Valid @RequestBody ArticleTopVO articleTopVO) {
        articleService.updateArticleTop(articleTopVO);
        return Result.ok();
    }

    @OptLog(optType = UPDATE)
    @IsLogin
    @ApiOperation(value = "恢复或删除文章")
    @PutMapping("/admin/articles")
    public Result<?> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO) {
        articleService.updateArticleDelete(deleteVO);
        return Result.ok();
    }

    @OptLog(optType = REMOVE)
    @IsLogin
    @ApiOperation(value = "物理删除文章")
    @DeleteMapping("/admin/articles")
    public Result<?> deleteArticles(@RequestBody List<Integer> articleIdList) {
        articleService.deleteArticles(articleIdList);
        return Result.ok();
    }
}
