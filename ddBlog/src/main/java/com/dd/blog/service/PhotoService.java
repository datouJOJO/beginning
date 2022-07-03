package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.PhotoBackDTO;
import com.dd.blog.dto.PhotoDTO;
import com.dd.blog.entity.Photo;
import com.dd.blog.vo.*;

import java.util.List;

/**
 * @author DD
 * @about 照片服务
 * @date 2022/5/2 19:56
 */

public interface PhotoService extends IService<Photo> {

    /**
     * 根据传入的
     * @return
     */
    PhotoDTO listPhotoById(Integer albumId);

    PageResult<PhotoBackDTO> listPhotos(ConditionVo condition);

    void updatePhoto(PhotoInfoVO photoInfoVO);

    void savePhotos(PhotoVO photoVO);

    void updatePhotosAlbum(PhotoVO photoVO);

    void updatePhotoDelete(DeleteVO deleteVO);

    void deletePhotos(List<Integer> photoIdList);
}
