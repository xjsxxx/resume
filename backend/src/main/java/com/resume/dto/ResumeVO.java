package com.resume.dto;

import com.resume.entity.Award;
import com.resume.entity.BasicInfo;
import com.resume.entity.Education;
import com.resume.entity.Experience;
import com.resume.entity.Project;
import com.resume.entity.Skill;
import lombok.Data;

import java.util.List;

/**
 * 前台简历聚合数据：一次请求返回整页内容
 */
@Data
public class ResumeVO {

    private BasicInfo basic;
    private List<Education> education;
    private List<Experience> experience;
    private List<Project> project;
    private List<Skill> skill;
    private List<Award> award;
}
