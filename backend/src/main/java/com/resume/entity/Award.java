package com.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 荣誉证书
 */
@Data
@TableName("award")
public class Award {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 奖项名称 */
    private String name;

    /** 级别，如 国家级/校级 */
    private String level;

    /** 获奖时间 */
    private String date;

    /** 颁发单位 */
    private String issuer;

    /** 排序，越小越靠前 */
    private Integer sortOrder;
}
