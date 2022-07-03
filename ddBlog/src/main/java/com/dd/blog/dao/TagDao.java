package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.dto.TagBackDTO;
import com.dd.blog.entity.Tag;
import com.dd.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签
 * @author DD
 * @date 2022/4/5 1:04
 */

@Repository
public interface TagDao extends BaseMapper<Tag> {
    List<TagBackDTO> listTagBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

    List<String> listTagNameByArticleId(Integer articleId);
}
