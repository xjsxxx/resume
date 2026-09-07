package com.resume.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.resume.common.BizException;
import com.resume.common.Result;
import com.resume.entity.Skill;
import com.resume.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专业技能管理
 */
@RestController
@RequestMapping("/api/admin/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public Result<List<Skill>> list() {
        return Result.ok(skillService.list(new QueryWrapper<Skill>().orderByAsc("sort_order")));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Skill skill) {
        if (skill == null || !StringUtils.hasText(skill.getName())) {
            throw new BizException("技能名称不能为空");
        }
        if (skill.getSortOrder() == null) {
            skill.setSortOrder(0);
        }
        if (skill.getLevel() == null) {
            skill.setLevel(0);
        }
        skill.setId(null);
        skillService.save(skill);
        return Result.ok();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Skill skill) {
        if (skill == null || skill.getId() == null) {
            throw new BizException("缺少记录 id");
        }
        skillService.updateById(skill);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!skillService.removeById(id)) {
            throw new BizException("删除失败，记录不存在");
        }
        return Result.ok();
    }
}
