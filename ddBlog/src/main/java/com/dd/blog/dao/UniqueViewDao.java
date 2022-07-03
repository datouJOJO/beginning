package com.dd.blog.dao;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.dto.UniqueViewDTO;
import com.dd.blog.vo.UniqueView;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/5/7 14:15
 */

@Repository
public interface UniqueViewDao extends BaseMapper<UniqueView> {
    List<UniqueViewDTO> listUniqueViews(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);
}
