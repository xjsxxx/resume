package com.resume.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.resume.common.BizException;
import com.resume.common.Result;
import com.resume.entity.Experience;
import com.resume.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作/实习经历管理
 */
@RestController
@RequestMapping("/api/admin/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping
    public Result<List<Experience>> list() {
        return Result.ok(experienceService.list(new QueryWrapper<Experience>().orderByAsc("sort_order")));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Experience experience) {
        if (experience == null || !StringUtils.hasText(experience.getCompany())) {
            throw new BizException("公司名称不能为空");
        }
        if (experience.getSortOrder() == null) {
            experience.setSortOrder(0);
        }
        if (!StringUtils.hasText(experience.getType())) {
            experience.setType("工作");
        }
        experience.setId(null);
        experienceService.save(experience);
        return Result.ok();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Experience experience) {
        if (experience == null || experience.getId() == null) {
            throw new BizException("缺少记录 id");
        }
        experienceService.updateById(experience);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!experienceService.removeById(id)) {
            throw new BizException("删除失败，记录不存在");
        }
        return Result.ok();
    }
}
