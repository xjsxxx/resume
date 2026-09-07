package com.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 教育经历
 */
@Data
@TableName("education")
public class Education {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 学校 */
    private String school;

    /** 专业 */
    private String major;

    /** 学历 */
    private String degree;

    /** 开始时间，如 2019-09 */
    private String startDate;

    /** 结束时间 */
    private String endDate;

    /** 描述 */
    private String description;

    /** 排序，越小越靠前 */
    private Integer sortOrder;
}
