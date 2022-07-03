package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.OperationLogDTO;
import com.dd.blog.entity.OperationLog;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;

/**
 * @author DD
 * @about
 * @date 2022/5/18 17:29
 */
public interface OperationLogService extends IService<OperationLog> {
    PageResult<OperationLogDTO> listOperationLogs(ConditionVo conditionVO);
}
