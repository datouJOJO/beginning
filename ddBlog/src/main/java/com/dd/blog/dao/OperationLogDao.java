package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.OperationLog;
import org.springframework.stereotype.Repository;

/**
 * @author DD
 * @about
 * @date 2022/5/18 17:23
 */

@Repository
public interface OperationLogDao extends BaseMapper<OperationLog> {
}
