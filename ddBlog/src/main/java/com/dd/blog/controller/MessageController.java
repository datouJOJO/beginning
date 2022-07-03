package com.dd.blog.controller;

import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dto.MessageBackDTO;
import com.dd.blog.service.MessageService;
import com.dd.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import static com.dd.blog.constant.OptTypeConst.*;
/**
 * @author DD
 * @about 留言控制器
 * @date 2022/5/2 16:39
 */

@RestController
@Api(tags = "留言模块")
public class MessageController {

    @Autowired
    public MessageService messageService;

    @GetMapping("/messages")
    @ApiOperation(value = "查询留言")
    public <T> Result listMessage() {
        return Result.ok(messageService.listMessages());
    }

    @PostMapping("/messages")
    @ApiOperation("保存留言")
    @IsLogin
    public <T> Result saveMessage(@RequestBody MessageVO messageVO) {
        messageService.saveMessage(messageVO);
        return Result.ok();
    }

    @ApiOperation(value = "查看后台留言列表")
    @GetMapping("/admin/messages")
    public Result<PageResult<MessageBackDTO>> listMessageBackDTO(ConditionVo condition) {
        return Result.ok(messageService.listMessageBackDTO(condition));
    }

    @OptLog(optType = UPDATE)
    @IsLogin
    @ApiOperation(value = "审核留言")
    @PutMapping("/admin/messages/review")
    public Result<?> updateMessagesReview(@Valid @RequestBody ReviewVO reviewVO) {
        messageService.updateMessagesReview(reviewVO);
        return Result.ok();
    }

    @OptLog(optType = REMOVE)
    @IsLogin
    @ApiOperation(value = "删除留言")
    @DeleteMapping("/admin/messages")
    public Result<?> deleteMessages(@RequestBody List<Integer> messageIdList) {
        messageService.removeByIds(messageIdList);
        return Result.ok();
    }
}
