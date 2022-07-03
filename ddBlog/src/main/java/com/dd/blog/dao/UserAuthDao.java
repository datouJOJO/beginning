package com.dd.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd.blog.dto.UserBackDTO;
import com.dd.blog.entity.UserAuth;
import com.dd.blog.vo.ConditionVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author DD
 * @about
 * @date 2022/4/24 18:44
 */

@Repository
public interface UserAuthDao extends BaseMapper<UserAuth> {
    Integer countUser(@Param("condition") ConditionVo condition);

    List<UserBackDTO> listUsers(@Param("current")Long limitCurrent,@Param("size") Long size,@Param("condition") ConditionVo condition);
}
