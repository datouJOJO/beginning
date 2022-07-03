package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dao.PhotoAlbumsDao;
import com.dd.blog.dao.PhotoDao;
import com.dd.blog.dto.PhotoAlbumBackDTO;
import com.dd.blog.dto.PhotoAlbumDTO;
import com.dd.blog.dto.PhotoAlbumsDTO;
import com.dd.blog.entity.Photo;
import com.dd.blog.entity.PhotoAlbums;
import com.dd.blog.service.PhotoAlbumsService;
import com.dd.blog.utils.BeanCopyUtils;
import com.dd.blog.utils.PageUtils;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.PhotoAlbumVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

import static com.dd.blog.constant.CommonConst.*;
import static com.dd.blog.emuns.ArticleStatusEnum.*;
import static com.dd.blog.constant.OptTypeConst.*;
/**
 * @author DD
 * @about
 * @date 2022/5/2 17:23
 */

@Service
public class PhotoAlbumsServiceImpl extends ServiceImpl<PhotoAlbumsDao, PhotoAlbums> implements PhotoAlbumsService {

    @Autowired
    private PhotoAlbumsDao photoAlbumsDao;

    @Autowired
    private PhotoDao photoDao;

    @Override
    public List<PhotoAlbumsDTO> listPhotoAlbums() {
        List<PhotoAlbums> photoAlbums = photoAlbumsDao.selectList(new LambdaQueryWrapper<PhotoAlbums>()
                .eq(PhotoAlbums::getStatus, PUBLIC.getStatus())
                .eq(PhotoAlbums::getIsDelete, FALSE)
                .orderByDesc(PhotoAlbums::getId));
        return BeanCopyUtils.copyList(photoAlbums, PhotoAlbumsDTO.class);
    }

    @Override
    public PageResult<PhotoAlbumBackDTO> listPhotoAlbumBacks(ConditionVo condition) {
        // 查询相册数量
        Integer count = photoAlbumsDao.selectCount(new LambdaQueryWrapper<PhotoAlbums>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), PhotoAlbums::getAlbumName, condition.getKeywords())
                .eq(PhotoAlbums::getIsDelete, FALSE));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询相册信息
        List<PhotoAlbumBackDTO> photoAlbumBackList = photoAlbumsDao.listPhotoAlbumBacks(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(photoAlbumBackList, count);
    }

    @Override
    public boolean saveOrUpdatePhotoAlbum(PhotoAlbumVO photoAlbumVO) {
        // 查询相册名是否存在
        PhotoAlbums album = photoAlbumsDao.selectOne(new LambdaQueryWrapper<PhotoAlbums>()
                .select(PhotoAlbums::getId)
                .eq(PhotoAlbums::getAlbumName, photoAlbumVO.getAlbumName()));
        if (Objects.nonNull(album) && !album.getId().equals(photoAlbumVO.getId())) {
            return false;
        }
        PhotoAlbums photoAlbum = BeanCopyUtils.copyObject(photoAlbumVO, PhotoAlbums.class);
        this.saveOrUpdate(photoAlbum);
        return true;
    }

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbumBackInfos() {
        List<PhotoAlbums> photoAlbumList = photoAlbumsDao.selectList(new LambdaQueryWrapper<PhotoAlbums>()
                .eq(PhotoAlbums::getIsDelete, FALSE));
        return BeanCopyUtils.copyList(photoAlbumList, PhotoAlbumDTO.class);
    }

    @Override
    public PhotoAlbumBackDTO getPhotoAlbumBackById(Integer albumId) {
        // 查询相册信息
        PhotoAlbums photoAlbum = photoAlbumsDao.selectById(albumId);
        // 查询照片数量
        Integer photoCount = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId)
                .eq(Photo::getIsDelete, FALSE));
        PhotoAlbumBackDTO album = BeanCopyUtils.copyObject(photoAlbum, PhotoAlbumBackDTO.class);
        album.setPhotoCount(photoCount);
        return album;
    }

    @Override
    public void deletePhotoAlbumById(Integer albumId) {
        // 查询照片数量
        Integer count = photoDao.selectCount(new LambdaQueryWrapper<Photo>()
                .eq(Photo::getAlbumId, albumId));
        if (count > 0) {
            // 若相册下存在照片则逻辑删除相册和照片
            photoAlbumsDao.updateById(PhotoAlbums.builder()
                    .id(albumId)
                    .isDelete(TRUE)
                    .build());
            photoDao.update(new Photo(), new LambdaUpdateWrapper<Photo>()
                    .set(Photo::getIsDelete, TRUE)
                    .eq(Photo::getAlbumId, albumId));
        } else {
            // 若相册下不存在照片则直接删除
            photoAlbumsDao.deleteById(albumId);
        }
    }


}
