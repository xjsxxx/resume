package com.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 工作/实习经历
 */
@Data
@TableName("experience")
public class Experience {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 公司 */
    private String company;

    /** 职位 */
    private String position;

    /** 类型：工作 / 实习 */
    private String type;

    /** 开始时间 */
    private String startDate;

    /** 结束时间 */
    private String endDate;

    /** 工作内容描述 */
    private String description;

    /** 排序，越小越靠前 */
    private Integer sortOrder;
}
