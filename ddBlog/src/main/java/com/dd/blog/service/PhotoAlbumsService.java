package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.PhotoAlbumBackDTO;
import com.dd.blog.dto.PhotoAlbumDTO;
import com.dd.blog.dto.PhotoAlbumsDTO;
import com.dd.blog.entity.PhotoAlbums;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.PhotoAlbumVO;

import java.util.List;

/**
 * @author DD
 * @about 相册服务
 * @date 2022/5/2 17:22
 */
public interface PhotoAlbumsService extends IService<PhotoAlbums> {
    List<PhotoAlbumsDTO> listPhotoAlbums();

    PageResult<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVo condition);

    boolean saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO);

    List<PhotoAlbumDTO> listPhotoAlbumBackInfos();

    PhotoAlbumBackDTO getPhotoAlbumBackById(Integer albumId);

    void deletePhotoAlbumById(Integer albumId);
}
