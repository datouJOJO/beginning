package com.dd.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dd.blog.dto.LabelOptionDTO;
import com.dd.blog.dto.ResourceDTO;
import com.dd.blog.entity.Resource;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.ResourceVO;

import java.util.List;

/**
 * @author DD
 * @about 资源服务
 * @date 2022/5/18 15:58
 */

public interface ResourceService extends IService<Resource> {
    List<ResourceDTO> listResources(ConditionVo conditionVO);

    boolean deleteResource(Integer resourceId);

    void saveOrUpdateResource(ResourceVO resourceVO);

    List<LabelOptionDTO> listResourceOption();
}
