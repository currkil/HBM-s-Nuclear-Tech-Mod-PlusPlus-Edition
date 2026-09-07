# HBM's Nuclear Tech Mod: PlusPlus Edition

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

**这是一个非官方的、将经典 1.7.10 版本 HBM 核科技模组忠实移植到 Minecraft 1.20.1 的复刻项目。**

中文(当前)/[English](/README_en.md)

本项目基于 [HBM 的原始模组](https://github.com/HbmMods/Hbm-s-Nuclear-Tech-Mod-GIT) 的代码和设计，目标是**按照生存发展顺序，将原版 1.7.10 的完整玩法与内容，以“原封不动”的宗旨迁移到高版本平台**。
> [!caution]
> 本项目目前处于早期开发阶段，建议在测试环境中使用。

> [!caution]
> 本项目目前正在逐步开发，不建议游玩，请等待开发较完全后再进行体验。

## 移植目标
- **玩法复刻**：完整保留原 1.7.10 版本的所有科技线、机器、武器、辐射系统及多方块结构。
- **体验优化**：在不改变核心机制的前提下，适配高版本 Forge API，提升稳定性和性能。
- **生存优先**：按照原版的生存发展顺序，逐步实现所有内容。

## Q&A
- Q1:本模组可以生存游玩吗？
- A1:目前来说并不可以。

## 开发者指南

本项目使用 Minecraft Forge 1.20.1 和 Gradle 构建，需要 JDK17 以上。

- 获取并设置项目环境
1.  **克隆仓库**：
    ```bash
    git clone https://github.com/currkil/HBM-s-Nuclear-Tech-Mod-PlusPlus-Edition.git
    ```
2.  **导入IDE**：使用 IntelliJ IDEA 或 Eclipse 作为项目文件夹导入。
3.  **生成运行配置**：
    *   **IntelliJ IDEA**: 在终端中运行 `./gradlew genIntellijRuns`。
    *   **Eclipse**: 运行 `./gradlew eclipse`。

- 不等待模组发布体验最新的模组
1.  **执行 编辑或使用此模组的代码 中的步骤**
2.  **运行客户端**：在 IDEA 中运行 `./gradlew runClient` 这个 Gradle 任务。

- 不等待模组发布自行编译最新的jar文件
1.  **执行 编辑或使用此模组的代码 中的步骤**
2.  **生成jar文件**：在 IDEA 中运行 `./gradlew build` 这个 Gradle 任务
3.  **寻找jar文件**：在 ` 项目/build/libs/ ` 中找到jar文件

## 许可证与致谢

> [!important]
> 本项目是 **HBM's Nuclear Tech Mod** 的衍生作品，严格遵守其 **GNU General Public License v3.0** 许可。

- **原始模组作者**: [HbmMods](https://github.com/HbmMods) 及所有 1.7.10 版本的贡献者。
- **特别致敬**: 所有为 HBM 模组高版本移植做出探索的社区开发者。

详细的许可证信息请见本仓库的 [LICENSE](./LICENSE) 文件。
