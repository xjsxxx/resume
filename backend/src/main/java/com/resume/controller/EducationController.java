package com.resume.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.resume.common.BizException;
import com.resume.common.Result;
import com.resume.entity.Education;
import com.resume.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教育经历管理
 */
@RestController
@RequestMapping("/api/admin/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping
    public Result<List<Education>> list() {
        return Result.ok(educationService.list(new QueryWrapper<Education>().orderByAsc("sort_order")));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Education education) {
        if (education == null || !StringUtils.hasText(education.getSchool())) {
            throw new BizException("学校不能为空");
        }
        if (education.getSortOrder() == null) {
            education.setSortOrder(0);
        }
        education.setId(null);
        educationService.save(education);
        return Result.ok();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Education education) {
        if (education == null || education.getId() == null) {
            throw new BizException("缺少记录 id");
        }
        educationService.updateById(education);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!educationService.removeById(id)) {
            throw new BizException("删除失败，记录不存在");
        }
        return Result.ok();
    }
}
