# SmartAccounting 智能记账宝

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9.20-purple.svg" alt="Kotlin">
  <img src="https://img.shields.io/badge/Compose-Multiplatform-1.5.x-blue.svg" alt="Compose">
  <img src="https://img.shields.io/badge/Platform-Android%204.0%2B-green.svg" alt="Platform">
  <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" alt="License">
</p>

## 项目简介

智能记账宝是一款基于 Kotlin Multiplatform (KMP) + Compose Multiplatform 构建的跨平台记账应用，支持 **Android** 和 **HarmonyOS** 双平台。

应用核心功能是自动识别支付宝和微信的支付成功页面，帮助用户轻松管理个人财务。

## 功能特性

### 1. 用户系统
- 手机号 + 验证码登录/注册
- 个人信息管理（头像、昵称、邮箱）
- 密码重置与账号注销

### 2. 自动记账（核心功能）
- Android/HarmonyOS 双平台无障碍服务
- 自动识别支付宝、微信支付成功页面
- 提取交易信息：金额、商户名称、交易时间、支付方式
- 智能标签匹配
- 悬浮窗确认界面
- 权限引导与保活设置

### 3. 账单管理
- 账单列表（按时间倒序）
- 手动添加/编辑/删除账单
- 批量操作功能
- 搜索与筛选
- 重复账单设置与提醒

### 4. 标签系统
- 12 个原生一级标签及二级标签
- 自定义标签（创建、编辑、删除）
- 标签颜色自定义

### 5. 数据分析
- 仪表盘首页：本月收支、预算进度、消费趋势
- 年/月/周收支统计
- 分类占比饼图、收支趋势折线图
- 支出排行榜
- 智能消费建议

### 6. 数据安全
- 生物识别解锁（指纹/面容）
- 本地数据加密
- 数据备份与恢复
- Excel/CSV 导出
- 深色模式、通知开关等个性化设置

## 技术架构

### 架构模式
- **MVVM** (Model-View-ViewModel)
- **Clean Architecture** - 清晰的分层架构

### 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Kotlin 1.9.20 |
| UI框架 | Compose Multiplatform 1.5.x |
| 数据库 | Room 2.6.1 |
| 依赖注入 | Koin 3.5.3 |
| 导航 | Navigation Compose |
| 异步 | Kotlin Coroutines + Flow |

## 编译运行

### 环境要求
- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17+
- Android SDK 34
- HarmonyOS 开发环境（用于 HarmonyOS 平台编译）

### Android 编译步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/SZDYMY/SmartAccounting.git
   cd SmartAccounting
   ```

2. **在 Android Studio 中打开项目**
   - File → Open → 选择项目根目录
   - 等待 Gradle 同步完成

3. **运行应用**
   - 选择 `app` 模块
   - 选择目标设备/模拟器
   - 点击 Run (▶)

### HarmonyOS 编译步骤

1. 在 DevEco Studio 中打开 `app/harmony` 目录
2. 配置签名信息
3. 运行/构建应用

## License

本项目采用 Apache License 2.0 许可证