package com.resume.dto;

import lombok.Data;

/**
 * 修改密码请求
 */
@Data
public class PasswordDTO {

    private String oldPassword;
    private String newPassword;
}
