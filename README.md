# SaaS Authority Backend

## 概述
SaaS Authority Backend 是一个基于 Spring Boot、Redis 和 MyBatis-Plus 的项目，旨在提供一个多租户管理、功能管理、登录、身份验证和功能分配等业务的 SaaS 平台。

## 技术栈
- **Spring Boot**: 用于构建独立运行的生产级 Spring 应用程序。
- **Redis**: 用于缓存和会话管理，以提高系统的响应速度和性能。
- **MyBatis-Plus**: 基于 MyBatis 的持久层框架，简化开发并提高效率。

## 功能模块
- **租户管理**: 支持多租户的创建、更新、删除和查询。
- **功能管理**: 管理系统中的功能模块及其权限。
- **登录与身份验证**: 提供用户登录、身份验证、Token 管理等功能。
- **功能分配**: 为不同的用户和租户分配相应的功能权限。

## 安装与运行

### 前提条件
- JDK 11 或以上版本
- Maven
- Redis 服务器
- MySQL 数据库

### 克隆项目
```bash
git clone https://github.com/LYF7314/sass-authority-backend.git
cd sass-authority-backend
```
### 构建运行项目
```bash
mvn clean install
mvn spring-boot:run
```
### 贡献
欢迎贡献代码！请遵循以下步骤：
- Fork 本项目。
- 创建你的分支 (git checkout -b feature/AmazingFeature)。
- 提交你的修改 (git commit -m 'Add some AmazingFeature')。
- 推送到分支 (git push origin feature/AmazingFeature)。
- 创建一个新的 Pull Request。

### 联系方式
如果你有任何问题或建议，请通过<bold><em>烧纸</em></bold>  联系我

