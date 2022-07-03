package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.MessageBackDTO;
import com.dd.blog.dto.MessageDTO;
import com.dd.blog.entity.Message;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.MessageVO;
import com.dd.blog.vo.PageResult;
import com.dd.blog.vo.ReviewVO;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/5/2 16:46
 */
public interface MessageService extends IService<Message> {

    List<MessageDTO> listMessages();

    void saveMessage(MessageVO messageVO);

    PageResult<MessageBackDTO> listMessageBackDTO(ConditionVo condition);

    void updateMessagesReview(ReviewVO reviewVO);
}
