package com.dd.blog.controller;

import com.dd.blog.annotation.GetPlaceInfo;
import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dto.PhotoBackDTO;
import com.dd.blog.service.PhotoService;
import com.dd.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.dd.blog.constant.OptTypeConst.*;

/**
 * @author DD
 * @about 照片控制器
 * @date 2022/5/2 19:53
 */

@RestController
@Api(tags = "照片模块")
public class PhotoController {

    @Autowired
    public PhotoService photoService;

    @GetMapping("/albums/{albumId}/photos")
    public <T>Result listPhotoById(@PathVariable("albumId") Integer albumId) {
        return Result.ok(photoService.listPhotoById(albumId));
    }

    @ApiOperation(value = "根据相册id获取照片列表")
    @GetMapping("/admin/photos")
    public Result<PageResult<PhotoBackDTO>> listPhotos(ConditionVo condition) {
        return Result.ok(photoService.listPhotos(condition));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新照片信息")
    @PutMapping("/admin/photos")
    @IsLogin
    public Result<?> updatePhoto(@Valid @RequestBody PhotoInfoVO photoInfoVO) {
        photoService.updatePhoto(photoInfoVO);
        return Result.ok();
    }

    @OptLog(optType = SAVE)
    @ApiOperation(value = "保存照片")
    @PostMapping("/admin/photos")
    @IsLogin
    public Result<?> savePhotos(@Valid @RequestBody PhotoVO photoVO) {
        photoService.savePhotos(photoVO);
        return Result.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "移动照片相册")
    @PutMapping("/admin/photos/album")
    @IsLogin
    public Result<?> updatePhotosAlbum(@Valid @RequestBody PhotoVO photoVO) {
        photoService.updatePhotosAlbum(photoVO);
        return Result.ok();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "更新照片删除状态")
    @PutMapping("/admin/photos/delete")
    @IsLogin
    public Result<?> updatePhotoDelete(@Valid @RequestBody DeleteVO deleteVO) {
        photoService.updatePhotoDelete(deleteVO);
        return Result.ok();
    }

    @OptLog(optType = REMOVE)
    @ApiOperation(value = "删除照片")
    @DeleteMapping("/admin/photos")
    @IsLogin
    public Result<?> deletePhotos(@RequestBody List<Integer> photoIdList) {
        photoService.deletePhotos(photoIdList);
        return Result.ok();
    }
}
