# HBM's Nuclear Tech Mod: PlusPlus Edition

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

**This is an unofficial, faithful port of the classic 1.7.10 HBM Nuclear Tech Mod to Minecraft 1.20.1.**

English (Current) / [Chinese](/README_en.md)

This project is based on the code and design of [HBM's original mod](https://github.com/HbmMods/Hbm-s-Nuclear-Tech-Mod-GIT), with the goal of **migrating the complete gameplay and content of the original 1.7.10 version to the high-version platform, following the survival progression order, with the tenet of keeping it "as-is".**
> [!caution]
> This project is currently in early development. It is recommended to use it in a test environment.

> [!caution]
> This project is currently under gradual development. It is not recommended for play. Please wait until development is more complete before experiencing it.

## Porting Goals
- **Gameplay Recreation**: Fully retain all tech trees, machines, weapons, radiation systems, and multiblock structures from the original 1.7.10 version.
- **Experience Optimization**: Adapt to the high-version Forge API without changing core mechanics, improving stability and performance.
- **Survival First**: Gradually implement all content according to the original version's survival progression order.

## Q&A
- Q1: Can this mod be played in survival?
- A1: Currently, no.

## Developer Guide

This project uses Minecraft Forge 1.20.1 and Gradle, requiring JDK 17 or above.

- Obtain and set up the project environment
1.  **Clone the repository**:
    ```bash
    git clone https://github.com/currkil/HBM-s-Nuclear-Tech-Mod-PlusPlus-Edition.git
    ```
2.  **Import into IDE**: Use IntelliJ IDEA or Eclipse to import the project folder.
3.  **Generate run configurations**:
    *   **IntelliJ IDEA**: Run `./gradlew genIntellijRuns` in the terminal.
    *   **Eclipse**: Run `./gradlew eclipse`.

- Experience the latest mod without waiting for a release
1.  **Follow the steps in "Edit or use this mod's code"**
2.  **Run the client**: Run the Gradle task `./gradlew runClient` in IDEA.

- Compile the latest jar file yourself without waiting for a release
1.  **Follow the steps in "Edit or use this mod's code"**
2.  **Generate the jar file**: Run the Gradle task `./gradlew build` in IDEA.
3.  **Find the jar file**: Locate it in `project/build/libs/`.

## License and Acknowledgements

> [!important]
> This project is a derivative work of **HBM's Nuclear Tech Mod** and strictly adheres to its **GNU General Public License v3.0**.

- **Original Mod Author**: [HbmMods](https://github.com/HbmMods) and all contributors to the 1.7.10 version.
- **Special Tribute**: To all community developers who have explored high-version ports of the HBM mod.

For detailed license information, please see the [LICENSE](./LICENSE) file in this repository.
