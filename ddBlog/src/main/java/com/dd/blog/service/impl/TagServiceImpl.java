package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.ArticleTagDao;
import com.dd.blog.dao.TagDao;
import com.dd.blog.dto.TagBackDTO;
import com.dd.blog.dto.TagDTO;
import com.dd.blog.entity.ArticleTag;
import com.dd.blog.entity.Tag;
import com.dd.blog.service.ArticleTagService;
import com.dd.blog.service.TagService;
import com.dd.blog.utils.BeanCopyUtils;
import com.dd.blog.utils.PageUtils;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author DD
 * @about
 * @date 2022/4/25 22:23
 */

@Service
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {

    @Autowired
    public TagDao tagDao;

    @Override
    public PageResult<TagDTO> listTags() {
        List<Tag> tagList = tagDao.selectList(null);
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList, TagDTO.class);
        Integer count = tagDao.selectCount(null);
        return new PageResult<>(tagDTOList, count);
    }

    @Override
    public List<TagDTO> listTagsBySearch(ConditionVo condition) {
        // 搜索标签
        List<Tag> tagList = tagDao.selectList(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords())
                .orderByDesc(Tag::getId));
        return BeanCopyUtils.copyList(tagList, TagDTO.class);
    }

    @Override
    public PageResult<TagBackDTO> listTagBackDTO(ConditionVo condition) {
        // 查询标签数量
        Integer count = tagDao.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagBackDTO> tagList = tagDao.listTagBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(tagList, count);
    }

    @Override
    public boolean saveOrUpdateTag(TagVO tagVO) {
        // 查询标签名是否存在
        Tag existTag = tagDao.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tagVO.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVO.getId())) {
            return false;
        }
        Tag tag = BeanCopyUtils.copyObject(tagVO, Tag.class);
        this.saveOrUpdate(tag);
        return true;
    }

    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public boolean deleteTag(List<Integer> tagIdList) {
        // 查询标签下是否有文章
        Integer count = articleTagDao.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, tagIdList));
        if (count > 0) {
            return false;
        }
        tagDao.deleteBatchIds(tagIdList);
        return true;
    }
}
