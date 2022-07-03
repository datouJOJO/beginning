package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.ArticleTag;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author DD
 * @about
 * @date 2022/5/8 15:45
 */

@Repository
public interface ArticleTagDao extends BaseMapper<ArticleTag> {
}
