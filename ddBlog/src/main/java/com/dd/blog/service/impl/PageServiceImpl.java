package com.dd.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dd.blog.dao.PageDao;
import com.dd.blog.entity.Page;
import com.dd.blog.service.PageService;
import com.dd.blog.service.RedisService;
import com.dd.blog.utils.BeanCopyUtils;
import com.dd.blog.vo.PageVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dd.blog.constant.RedisPrefixConst.*;

import java.util.List;
import java.util.Objects;

import static com.dd.blog.constant.RedisPrefixConst.*;

/**
 * @author DD
 * @date 2022/4/5 18:01
 */

@Log4j2
@Service
public class PageServiceImpl extends ServiceImpl<PageDao, Page> implements PageService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PageDao pageDao;

    @Override
    public List<PageVO> listPages() {
        List<PageVO> pageVOList;
        Object pageList = redisService.get(PAGE_COVER);
        //不为空就直接取
        if(Objects.nonNull(pageList)) {
            pageVOList = JSON.parseObject(pageList.toString(), List.class);
        } else {
            //为空查询数据库 然后更新缓存
            pageVOList = BeanCopyUtils.copyList(pageDao.selectList(null), PageVO.class);
//            log.info(pageDao.selectList(null));
            redisService.set(PAGE_COVER, JSON.toJSONString(pageVOList));
        }
        return pageVOList;
    }

    @Override
    public void saveOrUpdatePage(PageVO pageVO) {
        Page page = BeanCopyUtils.copyObject(pageVO, Page.class);
        this.saveOrUpdate(page);
        // 删除缓存
        redisService.del(PAGE_COVER);
    }

    @Override
    public void deletePage(Integer pageId) {
        pageDao.deleteById(pageId);
        // 删除缓存
        redisService.del(PAGE_COVER);
    }
}
