package com.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 专业技能
 */
@Data
@TableName("skill")
public class Skill {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 分类，如 后端/前端/数据库 */
    private String category;

    /** 技能名 */
    private String name;

    /** 熟练度 0-100 */
    private Integer level;

    /** 排序，越小越靠前 */
    private Integer sortOrder;
}
