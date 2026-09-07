package com.resume.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.resume.common.Result;
import com.resume.dto.ResumeVO;
import com.resume.entity.Award;
import com.resume.entity.BasicInfo;
import com.resume.entity.Education;
import com.resume.entity.Experience;
import com.resume.entity.Project;
import com.resume.entity.Skill;
import com.resume.service.AwardService;
import com.resume.service.BasicInfoService;
import com.resume.service.EducationService;
import com.resume.service.ExperienceService;
import com.resume.service.ProjectService;
import com.resume.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 访客公开接口：前台展示页数据（无需登录）
 */
@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private BasicInfoService basicInfoService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private AwardService awardService;

    /** 一次返回整页简历数据 */
    @GetMapping("/resume")
    public Result<ResumeVO> resume() {
        ResumeVO vo = new ResumeVO();
        vo.setBasic(basicInfoService.getById(1L));
        vo.setEducation(orderedList(educationService));
        vo.setExperience(orderedList(experienceService));
        vo.setProject(orderedList(projectService));
        vo.setSkill(orderedList(skillService));
        vo.setAward(orderedList(awardService));
        return Result.ok(vo);
    }

    private <T> List<T> orderedList(com.baomidou.mybatisplus.extension.service.IService<T> service) {
        // 各栏目表均有 sort_order 字段，统一按它升序返回
        return service.list(new QueryWrapper<T>().orderByAsc("sort_order"));
    }
}
