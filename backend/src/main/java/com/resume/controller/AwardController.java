package com.resume.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.resume.common.BizException;
import com.resume.common.Result;
import com.resume.entity.Award;
import com.resume.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 荣誉证书管理
 */
@RestController
@RequestMapping("/api/admin/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @GetMapping
    public Result<List<Award>> list() {
        return Result.ok(awardService.list(new QueryWrapper<Award>().orderByAsc("sort_order")));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Award award) {
        if (award == null || !StringUtils.hasText(award.getName())) {
            throw new BizException("奖项名称不能为空");
        }
        if (award.getSortOrder() == null) {
            award.setSortOrder(0);
        }
        award.setId(null);
        awardService.save(award);
        return Result.ok();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Award award) {
        if (award == null || award.getId() == null) {
            throw new BizException("缺少记录 id");
        }
        awardService.updateById(award);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        if (!awardService.removeById(id)) {
            throw new BizException("删除失败，记录不存在");
        }
        return Result.ok();
    }
}
