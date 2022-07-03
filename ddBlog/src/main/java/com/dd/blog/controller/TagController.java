package com.dd.blog.controller;

import com.dd.blog.annotation.IsLogin;
import com.dd.blog.annotation.OptLog;
import com.dd.blog.service.TagService;
import com.dd.blog.vo.ConditionVo;
import com.dd.blog.vo.Result;
import com.dd.blog.vo.TagVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import static com.dd.blog.constant.OptTypeConst.*;

/**
 * @author DD
 * @about 标签控制器
 * @date 2022/5/17 21:41
 */

@Api(tags = "标签模块")
@RestController
public class TagController {

    @Autowired
    public TagService tagService;

    @ApiOperation(value = "查询标签列表")
    @GetMapping("/tags")
    public <T>Result listTags() {
        return Result.ok(tagService.listTags());
    }

    @ApiOperation(value = "查询后台标签列表")
    @GetMapping("/admin/tags")
    public <T>Result listTagBackDTO(ConditionVo condition) {
        return Result.ok(tagService.listTagBackDTO(condition));
    }

    @ApiOperation(value = "搜索文章标签")
    @GetMapping("/admin/tags/search")
    public <T>Result listTagsBySearch(ConditionVo condition) {
        return Result.ok(tagService.listTagsBySearch(condition));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @IsLogin
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/admin/tags")
    public <T>Result saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.ok();
    }

    @OptLog(optType = REMOVE)
    @IsLogin
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/tags")
    public <T>Result deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.ok();
    }
}
