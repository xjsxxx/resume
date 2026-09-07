package com.resume.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.resume.common.BizException;
import com.resume.common.Result;
import com.resume.dto.LoginDTO;
import com.resume.dto.LoginVO;
import com.resume.dto.PasswordDTO;
import com.resume.entity.AdminUser;
import com.resume.service.AdminUserService;
import com.resume.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登录 / 账号相关接口
 */
@RestController
@RequestMapping("/api/admin/auth")
public class AuthController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /** 登录（无需 Token） */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        if (!StringUtils.hasText(dto.getUsername()) || !StringUtils.hasText(dto.getPassword())) {
            throw new BizException("用户名和密码不能为空");
        }
        AdminUser user = adminUserService.getOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, dto.getUsername()));
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException("用户名或密码错误");
        }
        LoginVO vo = new LoginVO();
        vo.setToken(jwtUtil.createToken(user.getId(), user.getUsername(), user.getNickname()));
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        return Result.ok(vo);
    }

    /** 获取当前登录人信息（需 Token） */
    @GetMapping("/me")
    public Result<LoginVO> me(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        AdminUser user = adminUserService.getOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, username));
        if (user == null) {
            throw new BizException("用户不存在");
        }
        LoginVO vo = new LoginVO();
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setAvatar(user.getAvatar());
        return Result.ok(vo);
    }

    /** 修改密码（需 Token） */
    @PutMapping("/password")
    public Result<Void> changePassword(@RequestBody PasswordDTO dto, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (!StringUtils.hasText(dto.getOldPassword()) || !StringUtils.hasText(dto.getNewPassword())) {
            throw new BizException("新旧密码不能为空");
        }
        AdminUser user = adminUserService.getOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, username));
        if (user == null || !passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BizException("原密码错误");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        adminUserService.updateById(user);
        return Result.ok();
    }
}
