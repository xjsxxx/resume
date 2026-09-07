package com.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 简历基本信息（单行，固定 id=1）
 */
@Data
@TableName("basic_info")
public class BasicInfo {

    @TableId(type = IdType.INPUT)
    private Long id;

    /** 姓名 */
    private String name;

    /** 求职意向/当前岗位 */
    private String jobTitle;

    /** 头像地址，空则前端用姓名首字占位 */
    private String avatarUrl;

    /** 出生年月 */
    private String birthDate;

    /** 所在城市 */
    private String city;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** Github */
    private String github;

    /** 经验时长，如 3年 */
    private String yearsOfExp;

    /** 个人简介 */
    private String summary;
}
