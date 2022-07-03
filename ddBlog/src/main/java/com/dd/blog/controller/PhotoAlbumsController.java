package com.dd.blog.controller;

import com.dd.blog.annotation.GetPlaceInfo;
import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dto.PhotoAlbumBackDTO;
import com.dd.blog.dto.PhotoAlbumDTO;
import com.dd.blog.service.PhotoAlbumsService;
import com.dd.blog.utils.QiniuUtils;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.PhotoAlbumVO;
import com.dd.blog.vo.Result;
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

/**
 * @author DD
 * @about
 * @date 2022/5/2 17:16
 */

@RestController
@Api(tags = "相册模块")
public class PhotoAlbumsController {

    @Autowired
    public PhotoAlbumsService photoAlbumsService;

    @Autowired
    public QiniuUtils qiniuUtils;

    @GetMapping("/photos/albums")
    @ApiOperation(value = "列出相册列表")
    public <T> Result listAlbums() {
        return Result.ok(photoAlbumsService.listPhotoAlbums());
    }

    @ApiOperation(value = "查看后台相册列表")
    @GetMapping("/admin/photos/albums")
    public Result<PageResult<PhotoAlbumBackDTO>> listPhotoAlbumBacks(ConditionVo condition) {
        return Result.ok(photoAlbumsService.listPhotoAlbumBacks(condition));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "保存或更新相册")
    @PostMapping("/admin/photos/albums")
    @IsLogin
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumsService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.ok();
    }

    @ApiOperation(value = "上传相册封面")
    @ApiImplicitParam(name = "file", value = "相册封面", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/photos/albums/cover")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            String url = QiniuUtils.url + fileName;
            return Result.ok(url);
        }
        return Result.fail(UPLOAD_FAIL.getMessage());
    }

    @ApiOperation(value = "获取后台相册列表信息")
    @GetMapping("/admin/photos/albums/info")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.ok(photoAlbumsService.listPhotoAlbumBackInfos());
    }

    @ApiOperation(value = "根据id获取后台相册信息")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @GetMapping("/admin/photos/albums/{albumId}/info")
    public Result<PhotoAlbumBackDTO> getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        return Result.ok(photoAlbumsService.getPhotoAlbumBackById(albumId));
    }

    @OptLog(optType = REMOVE)
    @ApiOperation(value = "根据id删除相册")
    @ApiImplicitParam(name = "albumId", value = "相册id", required = true, dataType = "Integer")
    @DeleteMapping("/admin/photos/albums/{albumId}")
    @IsLogin
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumsService.deletePhotoAlbumById(albumId);
        return Result.ok();
    }
}
