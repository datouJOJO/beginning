package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.CommentDao;
import com.dd.blog.dto.CommentBackDTO;
import com.dd.blog.dto.CommentDTO;
import com.dd.blog.dto.ReplyCountDTO;
import com.dd.blog.dto.ReplyDTO;
import com.dd.blog.entity.Comment;
import com.dd.blog.service.BlogInfoService;
import com.dd.blog.service.CommentService;
import com.dd.blog.service.RedisService;
import com.dd.blog.utils.HTMLUtils;
import com.dd.blog.utils.PageUtils;
import com.dd.blog.utils.UserThreadLocal;
import com.dd.blog.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.dd.blog.constant.CommonConst.*;
import static com.dd.blog.constant.RedisPrefixConst.*;

/**
 * @author DD
 * @about
 * @date 2022/5/1 16:13
 */

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BlogInfoService blogInfoService;

    @Override
    public PageResult<CommentDTO> listComments(CommentVO commentVO) {

        //查询评论量
        //查询所有父评论
        Integer commentCount = commentDao.selectCount(new LambdaQueryWrapper<Comment>()
                                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                                .eq(Comment::getType, commentVO.getType())
                                .isNull(Comment::getParentId)
                                .eq(Comment::getIsReview, TRUE));
        if(commentCount == 0) {
            return new PageResult<>();
        }
        //获取父评论对象
        List<CommentDTO> commentDTOList = commentDao.listComments(PageUtils.getCurrent() - 1, PageUtils.getSize(), commentVO);
        if(CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        //从redis获取评论点赞数据
        Map<String, Object> likeCountMap = redisService.hashGetAll(COMMENT_LIKE_COUNT);
        //根据父评论id获取子评论list
        List<Integer> commentParentIds = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        List<ReplyDTO> replyDTOList = commentDao.listReplies(commentParentIds);
        //根据评论id给评论设置点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId())));
        //根据父评论id对评论进行分组
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));
        //根据id查询回复量
        Map<Integer, Integer> replyCountMap = commentDao.listReplyCountByParentIds(commentParentIds)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        //封装数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentDTOList, commentCount);
    }

    @Override
    public void saveComment(CommentVO commentVO) {
        WebsiteConfigVO websiteConfig = blogInfoService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        commentVO.setCommentContent(HTMLUtils.deleteTag(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserThreadLocal.get().getUserId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isReview == TRUE ? FALSE : TRUE)
                .build();
        commentDao.insert(comment);
    }

    @Override
    public void saveCommentLike(Integer commentId) {
        String userLikeKey = COMMENT_USER_LIKE + UserThreadLocal.get().getUserId();
        if(redisService.sIsMember(userLikeKey, commentId)) {
            redisService.setRemove(userLikeKey, commentId);
            redisService.hashDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            redisService.setAdd(userLikeKey, commentId);
            redisService.hashIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    @Override
    public PageResult<CommentBackDTO> listCommentBackDTO(ConditionVo condition) {
        // 统计后台评论量
        Integer count = commentDao.countCommentDTO(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台评论集合
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(commentBackDTOList, count);
    }

    @Override
    public void updateCommentsReview(ReviewVO reviewVO) {
        // 修改评论审核状态
        List<Comment> commentList = reviewVO.getIdList().stream().map(item -> Comment.builder()
                .id(item)
                .isReview(reviewVO.getIsReview())
                .build())
                .collect(Collectors.toList());
        this.updateBatchById(commentList);
    }
}
