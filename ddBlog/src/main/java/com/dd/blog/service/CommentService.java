package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.CommentBackDTO;
import com.dd.blog.dto.CommentDTO;
import com.dd.blog.entity.Comment;
import com.dd.blog.vo.CommentVO;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.ReviewVO;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/5/1 16:13
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查询文章评论
     * @param commentVO
     * @return
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);

    void saveComment(CommentVO commentVO);

    void saveCommentLike(Integer commentId);

    PageResult<CommentBackDTO> listCommentBackDTO(ConditionVo condition);

    void updateCommentsReview(ReviewVO reviewVO);
}
