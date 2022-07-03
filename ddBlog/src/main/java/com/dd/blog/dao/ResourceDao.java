package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author DD
 * @about 资源数据持久层
 * @date 2022/5/18 16:01
 */

@Repository
public interface ResourceDao extends BaseMapper<Resource> {
}
