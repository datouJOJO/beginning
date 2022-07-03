package com.dd.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dd.blog.dao.TalkDao;
import com.dd.blog.entity.Talk;
import com.dd.blog.service.TalkService;
import com.dd.blog.utils.HTMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.dd.blog.emuns.TalkEnum.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DD
 * @date 2022/4/5 20:27
 */

@Service
public class TalkServiceImpl implements TalkService {

    @Autowired
    private TalkDao talkDao;

    @Override
    public List<String> listHomeTalks() {
        return talkDao.selectList(new LambdaQueryWrapper<Talk>()
                            .eq(Talk::getStatus, PUBLIC.getStatus())
                            .orderByDesc(Talk::getIsTop)
                            .orderByDesc(Talk::getId)
                            .last("limit 10"))
                .stream()
                .map(item -> item.getContent().length() > 200 ? HTMLUtils.deleteTag(item.getContent().substring(0, 200)) : HTMLUtils.deleteTag(item.getContent()))
                .collect(Collectors.toList());
    }
}
