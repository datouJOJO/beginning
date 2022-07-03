package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.Message;
import org.springframework.stereotype.Repository;

/**
 * 留言
 * @author DD
 * @date 2022/4/5 1:04
 */

@Repository
public interface MessageDao extends BaseMapper<Message> {
}
