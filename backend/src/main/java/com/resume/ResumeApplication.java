package com.resume;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 个人求职简历展示系统 - 启动类
 */
@SpringBootApplication
@MapperScan("com.resume.mapper")
public class ResumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
        System.out.println("================== 简历系统后端启动成功 http://localhost:8080 ==================");
    }
}
