# 个人求职简历展示系统

一个前后端分离的 **个人求职简历在线展示 + 内容管理** Web 系统：

- **前台（访客）**：现代单页风格展示简历 —— 基本信息 / 关于我 / 专业技能 / 教育经历 / 工作与实习经历 / 项目经历 / 荣誉证书，一次接口返回整页数据。
- **后台（管理员）**：登录后可在线**增、删、改**上述各栏目内容并控制排序，修改后前台即时生效。

## 技术栈

| 端 | 技术 |
|---|---|
| 前端 | Vue 3 + Vite + Vue Router + Pinia + Axios + Element Plus |
| 后端 | Java 8 + Spring Boot 2.7 + MyBatis-Plus + JWT + BCrypt |
| 数据库 | MySQL 8（utf8mb4） |

## 目录结构

```
profile/
├── backend/                 # Spring Boot 后端（端口 8080）
│   └── src/main/
│       ├── java/com/resume/ # 入口 / common / config / controller / service / mapper / entity / util / runner
│       └── resources/
│           ├── application.yml
│           └── db/schema.sql        # 建表脚本（幂等）
└── frontend/                # Vue3 前端（端口 5173，/api 代理到后端）
    └── src/
        ├── api/  router/  store/    # 请求封装 / 路由(登录守卫) / 登录态(Pinia)
        └── views/
            ├── public/PortfolioView.vue        # 前台展示页
            └── admin/LoginView / LayoutView
                   └── sections/*                # 各栏目 CRUD 管理页
```

## 环境要求

- JDK 1.8 + Maven 3.6+
- Node 18+ / npm
- MySQL 8，监听 `127.0.0.1:3306`，账号 `root`，密码 `xxxxxx`
  - 本机可复用正在运行的 Docker 容器已运行的mysql数据库（`mysql:8.0`），系统会自动创建独立数据库 `resume`，不影响其它项目任何数据。
  - 若你的 MySQL 账号密码不同：修改 [application.yml](backend/src/main/resources/application.yml) 中 `spring.datasource.username / password` 即可。

## 快速启动

> 数据库 `resume`、7 张表与种子数据均**自动初始化**，无需手工建库导数据。

### 1. 启动后端（端口 8080）

```bash
cd backend
mvn spring-boot:run
```

启动成功后控制台会打印初始化日志，内置：

- 管理员账号：**admin / admin123**
- 一套示例简历数据（姓名“张启航”等，可在后台随时修改）

### 2. 启动前端（端口 5173）

另开一个终端：

```bash
cd frontend
npm install     # 首次执行
npm run dev
```

浏览器访问：

| 地址 | 说明 |
|---|---|
| http://localhost:5173/ | 前台简历展示页（访客可见） |
| http://localhost:5173/admin/login | 后台登录（admin / admin123） |

## 生产构建（可选）

```bash
cd frontend && npm run build            # 产物在 frontend/dist
cd backend  && mvn -DskipTests package  # 产物 backend/target/resume-backend-1.0.0.jar
```

## 主要接口一览

- `GET  /api/public/resume` —— 前台聚合数据（公开）
- `POST /api/admin/auth/login` —— 登录，返回 JWT（公开）
- `GET  /api/admin/auth/me`、`PUT /api/admin/auth/password` —— 当前用户 / 改密
- `GET|POST|PUT|DELETE /api/admin/basic` —— 基本信息（单行）
- `GET|POST|PUT|DELETE /api/admin/{education|experience|project|skill|award}` —— 各栏目 CRUD
  - 除登录外的 `/api/admin/**` 均需请求头 `Authorization: Bearer <token>`

## 自定义配置

- **数据库 / JWT / 端口**：见 [application.yml](backend/src/main/resources/application.yml)
- **默认种子内容**：在 [DataInitializer.java](backend/src/main/java/com/resume/runner/DataInitializer.java) 中修改，删除已建库内容后重启会重新写入（表为空才写入，重复启动安全）
