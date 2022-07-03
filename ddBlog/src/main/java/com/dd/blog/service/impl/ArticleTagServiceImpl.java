package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.ArticleTagDao;
import com.dd.blog.entity.ArticleTag;
import com.dd.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @author DD
 * @about
 * @date 2022/5/8 15:52
 */

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {
}
