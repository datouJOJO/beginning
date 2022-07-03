package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.dto.CommentBackDTO;
import com.dd.blog.dto.CommentDTO;
import com.dd.blog.dto.ReplyCountDTO;
import com.dd.blog.dto.ReplyDTO;
import com.dd.blog.entity.Comment;
import com.dd.blog.vo.CommentVO;
import com.dd.blog.vo.ConditionVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author DD
 * @about
 * @date 2022/5/1 16:26
 */

@Repository
public interface CommentDao extends BaseMapper<Comment> {
    /**
     * 列出当前对象所有的父评论
     * @param current
     * @param size
     * @param commentVO
     * @return
     */
    List<CommentDTO> listComments(@Param("current") long current,@Param("size") Long size,@Param("commentVO") CommentVO commentVO);

    /**
     * 根据父评论list列出所有的子评论列表
     * 此时对子评论对应哪个父评论不细分
     * @param commentParentIds
     * @return
     */
    List<ReplyDTO> listReplies(@Param("commentParentIds") List<Integer> commentParentIds);

    /**
     * 根据parentId查询回复量
     * @param commentParentIds
     * @return
     */
    List<ReplyCountDTO> listReplyCountByParentIds(@Param("commentParentIds") List<Integer> commentParentIds);

    Integer countCommentDTO(@Param("condition") ConditionVo condition);

    List<CommentBackDTO> listCommentBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);
}
