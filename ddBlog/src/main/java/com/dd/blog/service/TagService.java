package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.TagBackDTO;
import com.dd.blog.dto.TagDTO;
import com.dd.blog.entity.Tag;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.TagVO;

import java.util.List;

/**
 * @author DD
 * @about 标签服务
 * @date 2022/4/25 22:22
 */


public interface TagService extends IService<Tag> {

    /**
     * 获取tag列表
     * 还有列表的长度
     * @return
     */
    PageResult<TagDTO> listTags();

    List<TagDTO> listTagsBySearch(ConditionVo condition);

    PageResult<TagBackDTO> listTagBackDTO(ConditionVo condition);

    boolean saveOrUpdateTag(TagVO tagVO);

    boolean deleteTag(List<Integer> tagIdList);
}
