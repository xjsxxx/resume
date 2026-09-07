package com.resume.controller;

import com.resume.common.BizException;
import com.resume.common.Result;
import com.resume.entity.BasicInfo;
import com.resume.service.BasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 基本信息维护（单行，固定 id=1）
 */
@RestController
@RequestMapping("/api/admin/basic")
public class BasicInfoController {

    /** 基本信息固定主键 */
    private static final Long BASIC_ID = 1L;

    @Autowired
    private BasicInfoService basicInfoService;

    @GetMapping
    public Result<BasicInfo> get() {
        return Result.ok(basicInfoService.getById(BASIC_ID));
    }

    @PutMapping
    public Result<Void> update(@RequestBody BasicInfo basic) {
        if (basic == null) {
            throw new BizException("内容不能为空");
        }
        basic.setId(BASIC_ID);
        if (basicInfoService.getById(BASIC_ID) != null) {
            basicInfoService.updateById(basic);
        } else {
            basicInfoService.save(basic);
        }
        return Result.ok();
    }
}
