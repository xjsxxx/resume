package com.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 项目经历
 */
@Data
@TableName("project")
public class Project {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 项目名称 */
    private String name;

    /** 担任角色 */
    private String role;

    /** 技术栈，逗号分隔 */
    private String techStack;

    /** 项目链接 */
    private String link;

    /** 项目描述 */
    private String description;

    /** 开始时间 */
    private String startDate;

    /** 结束时间 */
    private String endDate;

    /** 排序，越小越靠前 */
    private Integer sortOrder;
}
