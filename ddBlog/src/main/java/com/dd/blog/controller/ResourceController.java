package com.dd.blog.controller;

import com.dd.blog.dto.LabelOptionDTO;
import com.dd.blog.dto.ResourceDTO;
import com.dd.blog.service.ResourceService;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.ResourceVO;
import com.dd.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author DD
 * @about 资源控制器
 * @date 2022/5/18 15:12
 */

@Api(tags = "资源模块")
@RestController
public class ResourceController {

    @Autowired
    public ResourceService resourceService;

    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVo conditionVO) {
        return Result.ok(resourceService.listResources(conditionVO));
    }

    @ApiOperation(value = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        if(resourceService.deleteResource(resourceId)) {
            return Result.ok();
        } else {
            return Result.fail("该资源下存在角色 删除失败");
        }
    }

    @ApiOperation(value = "新增或修改资源")
    @PostMapping("/admin/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.ok();
    }

    @ApiOperation(value = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public Result<List<LabelOptionDTO>> listResourceOption() {
        return Result.ok(resourceService.listResourceOption());
    }

}
