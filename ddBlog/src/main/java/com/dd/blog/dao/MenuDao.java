package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.entity.Menu;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/5/6 12:34
 */

@Repository
public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> listMenusByUserInfoId(@Param("userId") Integer userId);
}
