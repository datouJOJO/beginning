package com.dd.blog.controller;

import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.dto.CommentBackDTO;
import com.dd.blog.service.CommentService;
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
 * @about 评论控制器
 * @date 2022/5/1 12:36
 */


@Api(tags = "评论模块")
@RestController
public class CommentController {

    @Autowired
    public CommentService commentService;

    @GetMapping("/comments")
    @ApiOperation(value = "查询一个文章中的评论列表 每个评论最多显示三个回复")
    public <T>Result listComments(CommentVO commentVO) {
        return Result.ok(commentService.listComments(commentVO));
    }

    @PostMapping("/comments")
    @ApiOperation(value = "保存用户评论")
    @IsLogin
    public <T>Result saveComment(@RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.ok();
    }

    @PostMapping("/comments/{commentId}/like")
    @ApiOperation(value = "评论点赞")
    @IsLogin
    public <T>Result saveCommentLike(@PathVariable("commentId") Integer commentId) {
        commentService.saveCommentLike(commentId);
        return Result.ok();
    }

    @ApiOperation(value = "查询后台评论")
    @GetMapping("/admin/comments")
    public Result<PageResult<CommentBackDTO>> listCommentBackDTO(ConditionVo condition) {
        return Result.ok(commentService.listCommentBackDTO(condition));
    }

    @OptLog(optType = REMOVE)
    @IsLogin
    @ApiOperation(value = "删除评论")
    @DeleteMapping("/admin/comments")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.ok();
    }

    @OptLog(optType = UPDATE)
    @IsLogin
    @ApiOperation(value = "审核评论")
    @PutMapping("/admin/comments/review")
    public Result<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.ok();
    }
}
