package com.resume.runner;

import com.resume.entity.AdminUser;
import com.resume.entity.Award;
import com.resume.entity.BasicInfo;
import com.resume.entity.Education;
import com.resume.entity.Experience;
import com.resume.entity.Project;
import com.resume.entity.Skill;
import com.resume.service.AdminUserService;
import com.resume.service.AwardService;
import com.resume.service.BasicInfoService;
import com.resume.service.EducationService;
import com.resume.service.ExperienceService;
import com.resume.service.ProjectService;
import com.resume.service.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 首次启动时写入种子数据（各表为空才写，可重复启动）
 * 默认管理员：admin / admin123
 */
@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private BasicInfoService basicInfoService;
    @Autowired
    private EducationService educationService;
    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private AwardService awardService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdmin();
        seedBasic();
        seedEducation();
        seedExperience();
        seedProject();
        seedSkill();
        seedAward();
        log.info("种子数据初始化完成（默认账号 admin/admin123）");
    }

    private void seedAdmin() {
        if (adminUserService.count() > 0) {
            return;
        }
        AdminUser admin = new AdminUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setNickname("管理员");
        admin.setAvatar("");
        adminUserService.save(admin);
        log.info("初始化管理员账号 admin/admin123");
    }

    private void seedBasic() {
        if (basicInfoService.getById(1L) != null) {
            return;
        }
        BasicInfo b = new BasicInfo();
        b.setId(1L);
        b.setName("张启航");
        b.setJobTitle("Java 后端开发工程师");
        b.setAvatarUrl("");
        b.setBirthDate("2001-08");
        b.setCity("上海市");
        b.setPhone("138-8888-6666");
        b.setEmail("qihang.zhang@example.com");
        b.setGithub("https://github.com/qihang-dev");
        b.setYearsOfExp("应届 · 2024 届");
        b.setSummary("热爱技术、注重细节的后端方向毕业生。具备扎实的 Java 基础与 Spring 全家桶项目实践，"
                + "熟悉 MySQL/Redis 等数据组件，掌握 Vue 等前端技术，能独立完成前后端联调。"
                + "学习能力强、乐于团队协作，期望在专业团队中持续成长，成为一名可靠的软件工程师。");
        basicInfoService.save(b);
    }

    private void seedEducation() {
        if (educationService.count() > 0) {
            return;
        }
        Education e = new Education();
        e.setSchool("江城大学");
        e.setMajor("软件工程");
        e.setDegree("本科");
        e.setStartDate("2020-09");
        e.setEndDate("2024-06");
        e.setDescription("主修课程：Java 程序设计、数据结构与算法、数据库原理、计算机网络、操作系统。\n"
                + "连续三年获校级一等奖学金，毕业设计《基于 Spring Boot 的在线预约系统》获评院级优秀。");
        e.setSortOrder(0);
        educationService.save(e);
    }

    private void seedExperience() {
        if (experienceService.count() > 0) {
            return;
        }
        Experience x = new Experience();
        x.setCompany("某电商平台公司");
        x.setPosition("Java 开发实习生");
        x.setType("实习");
        x.setStartDate("2023-06");
        x.setEndDate("2023-09");
        x.setDescription("· 参与订单结算模块开发，使用 Spring Boot + MyBatis-Plus 完成接口编写与单元测试。\n"
                + "· 优化商品列表查询，引入 Redis 缓存后接口平均响应从 220ms 降至 60ms。\n"
                + "· 配合前端完成联调与缺陷修复，获得 Mentor 好评。");
        x.setSortOrder(0);
        experienceService.save(x);
    }

    private void seedProject() {
        if (projectService.count() > 0) {
            return;
        }
        Project p1 = new Project();
        p1.setName("个人博客系统");
        p1.setRole("全栈开发");
        p1.setTechStack("Spring Boot, MyBatis-Plus, MySQL, Redis, Vue3");
        p1.setLink("https://github.com/qihang-dev/blog");
        p1.setStartDate("2023-03");
        p1.setEndDate("2023-05");
        p1.setDescription("前后端分离的博客系统：后端基于 Spring Boot 实现文章/评论/分类 REST 接口，集成 JWT 登录鉴权；"
                + "前端使用 Vue3 + Pinia 构建，支持 Markdown 渲染、搜索与访问统计。");
        p1.setSortOrder(0);

        Project p2 = new Project();
        p2.setName("个人求职简历展示系统（本项目）");
        p2.setRole("后端开发与数据库设计");
        p2.setTechStack("Vue3, Element Plus, Spring Boot, MyBatis-Plus, MySQL, JWT");
        p2.setLink("");
        p2.setStartDate("2024-03");
        p2.setEndDate("2024-04");
        p2.setDescription("求职简历内容的在线管理系统：访客页展示基础信息/教育/经历/技能/荣誉等栏目；"
                + "管理端登录后可在线增删改内容，前后端分离、数据持久化于 MySQL。");
        p2.setSortOrder(1);

        projectService.save(p1);
        projectService.save(p2);
    }

    private void seedSkill() {
        if (skillService.count() > 0) {
            return;
        }
        saveSkill("后端", "Java", 90, 0);
        saveSkill("后端", "Spring Boot", 85, 1);
        saveSkill("后端", "MyBatis-Plus", 82, 2);
        saveSkill("后端", "Spring Security / JWT", 75, 3);
        saveSkill("前端", "Vue3", 78, 0);
        saveSkill("前端", "HTML / CSS / JavaScript", 82, 1);
        saveSkill("数据库与中间件", "MySQL", 85, 0);
        saveSkill("数据库与中间件", "Redis", 72, 1);
        saveSkill("工具", "Git / Maven", 88, 0);
        saveSkill("工具", "Linux / Docker", 70, 1);
    }

    private void saveSkill(String category, String name, int level, int sort) {
        Skill s = new Skill();
        s.setCategory(category);
        s.setName(name);
        s.setLevel(level);
        s.setSortOrder(sort);
        skillService.save(s);
    }

    private void seedAward() {
        if (awardService.count() > 0) {
            return;
        }
        Award a1 = new Award();
        a1.setName("校级一等奖学金（连续三年）");
        a1.setLevel("校级");
        a1.setDate("2023-05");
        a1.setIssuer("江城大学");
        a1.setSortOrder(0);

        Award a2 = new Award();
        a2.setName("“互联网+”大学生创新创业大赛省赛铜奖");
        a2.setLevel("省级");
        a2.setDate("2022-09");
        a2.setIssuer("省教育厅");
        a2.setSortOrder(1);

        Award a3 = new Award();
        a3.setName("计算机软件能力认证（CSP）300 分");
        a3.setLevel("国家级");
        a3.setDate("2022-03");
        a3.setIssuer("中国计算机学会");
        a3.setSortOrder(2);

        awardService.save(a1);
        awardService.save(a2);
        awardService.save(a3);
    }
}
