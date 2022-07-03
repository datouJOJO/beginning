package com.dd.blog.controller;

import com.dd.blog.dto.OperationLogDTO;
import com.dd.blog.service.OperationLogService;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DD
 * @about 日志控制器
 * @date 2022/5/18 17:28
 */

@Api(tags = "日志模块")
@RestController
public class LogController {

    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public Result<PageResult<OperationLogDTO>> listOperationLogs(ConditionVo conditionVO) {
        return Result.ok(operationLogService.listOperationLogs(conditionVO));
    }

}
