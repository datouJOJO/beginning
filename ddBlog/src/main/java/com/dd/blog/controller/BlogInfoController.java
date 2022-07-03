package com.dd.blog.controller;

import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.service.BlogInfoService;
import com.dd.blog.utils.QiniuUtils;
import com.dd.blog.vo.BlogInfoVO;
import com.dd.blog.vo.Result;
import com.dd.blog.vo.WebsiteConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

import static com.dd.blog.constant.OptTypeConst.*;
import static com.dd.blog.emuns.StatusCodeEnum.UPLOAD_FAIL;

/**
 * 博客信息控制器
 * @author DD
 * @date 2022/4/3 20:00
 */
@Api(tags = "博客信息模块")
@RestController()
public class BlogInfoController {

    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private QiniuUtils qiniuUtils;

    @GetMapping("/admin")
    @ApiOperation(value = "查看后台信息")
    public <T>Result getAdminInfo() {
        return Result.ok(blogInfoService.getAdminInfo());
    }

    @ApiOperation(value = "查看博客信息")
    @GetMapping("/")
    public <T> Result getBlogInfo() {
        return Result.ok(blogInfoService.getBlogHomeInfo());
    }

    @ApiOperation(value = "上传博客配置图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/config/images")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            String url = QiniuUtils.url + fileName;
            return Result.ok(url);
        }
        return Result.fail(UPLOAD_FAIL.getMessage());
    }

    /**
     * 上传访客信息
     *
     * @return {@link Result}
     */
    @ApiOperation(value = "上传访客信息")
    @PostMapping("/report")
    public <T> Result report() {
        blogInfoService.report();
        return Result.ok();
    }

    @ApiOperation(value = "更新网站配置")
    @PutMapping("/admin/website/config")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        blogInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.ok();
    }

    @ApiOperation(value = "获取网站配置")
    @GetMapping("/admin/website/config")
    public Result<WebsiteConfigVO> getWebsiteConfig() {
        return Result.ok(blogInfoService.getWebsiteConfig());
    }

    @ApiOperation(value = "查看关于我信息")
    @GetMapping("/about")
    public Result<String> getAbout() {
        return Result.ok(blogInfoService.getAbout());
    }

    @OptLog(optType = UPDATE)
    @IsLogin
    @ApiOperation(value = "修改关于我信息")
    @PutMapping("/admin/about")
    public Result<?> updateAbout(@Valid @RequestBody BlogInfoVO blogInfoVO) {
        blogInfoService.updateAbout(blogInfoVO);
        return Result.ok();
    }
}
