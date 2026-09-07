package com.resume.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.resume.common.BizException;
import com.resume.common.Result;
import com.resume.entity.Project;
import com.resume.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目经历管理
 */
@RestController
@RequestMapping("/api/admin/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public Result<List<Project>> list() {
        return Result.ok(projectService.list(new QueryWrapper<Project>().orderByAsc("sort_order")));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Project project) {
        if (project == null || !StringUtils.hasText(project.getName())) {
            throw new BizException("项目名称不能为空");
        }
        if (project.getSortOrder() == null) {
            project.setSortOrder(0);
        }
        project.setId(null);
        projectService.save(project);
        return Result.ok();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Project project) {
        if (project == null || project.getId() == null) {
            throw new BizException("缺少记录 id");
        }
        projectService.updateById(project);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!projectService.removeById(id)) {
            throw new BizException("删除失败，记录不存在");
        }
        return Result.ok();
    }
}
