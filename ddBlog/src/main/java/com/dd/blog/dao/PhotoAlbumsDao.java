package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.dto.PhotoAlbumBackDTO;
import com.dd.blog.entity.PhotoAlbums;
import com.dd.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/5/2 17:20
 */
@Repository
public interface PhotoAlbumsDao extends BaseMapper<PhotoAlbums>{
    List<PhotoAlbumBackDTO> listPhotoAlbumBacks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);
}

