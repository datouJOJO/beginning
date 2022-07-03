package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.Talk;
import org.springframework.stereotype.Repository;

/**
 * @author DD
 * @date 2022/4/5 20:32
 */

@Repository
public interface TalkDao extends BaseMapper<Talk> {
}
