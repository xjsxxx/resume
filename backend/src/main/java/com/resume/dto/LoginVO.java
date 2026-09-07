package com.resume.dto;

import lombok.Data;

/**
 * 登录成功返回
 */
@Data
public class LoginVO {

    private String token;
    private String username;
    private String nickname;
    private String avatar;
}
