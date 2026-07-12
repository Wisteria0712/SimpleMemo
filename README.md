<div align="center">
  <img src="asserts/logo.png" alt="SimpleMemo Logo" width="160" />
</div>

<h1 align="center">SimpleMemo</h1>

<p align="center">一个基于 Java Swing 的本地桌面备忘录练手项目</p>

<p align="center">
  <a href="./README.md">简体中文</a> |
  <a href="./README_EN.md">English</a>
</p>

## 项目简介

SimpleMemo 是一个偏轻量的桌面备忘录程序，核心目标很直接：把日常要记的标题和内容保存下来，并提供增、改、删、导出这些基础能力。项目整体采用
Java 原生图形界面方案实现，没有引入复杂框架，适合作为一个入门阶段的桌面应用练手案例来阅读。

从代码实现上看，它主要围绕下面这条链路展开：

1. 启动 `App.java` 进入主界面。
2. 主界面通过 `JTable` 读取并展示本地备忘数据。
3. 通过独立窗口完成新增、修改操作。
4. 借助 Java 对象序列化把数据持久化到本地文件。
5. 支持把全部备忘导出为桌面上的 `.txt` 文本文件。

## 功能说明

### 1. 备忘列表展示

- 主窗口会读取本地 `src/data.txt` 中的备忘数据。
- 以表格形式展示两列信息：`标题` 与 `正文`。
- 当数据条数较多时，可通过滚动区域浏览全部内容。

### 2. 新增备忘

- 点击主界面的 `添加` 按钮进入新增窗口。
- 用户需要填写标题和内容，两项都不能为空。
- 点击保存后，会先进行一次确认，再把数据写入本地文件。

### 3. 修改备忘

- 在主界面先选中某一条记录，再点击 `修改`。
- 修改窗口会自动回填当前备忘的标题和内容。
- 确认修改后，会把内存中的对象更新，并将完整集合重新写回文件。

### 4. 删除备忘

- 在主界面选中目标记录后点击 `删除`。
- 删除前会弹出确认框，避免误删。
- 删除完成后，主界面会重新刷新，表格内容同步更新。

### 5. 导出文本

- 菜单栏 `功能 -> 导出` 可将当前全部备忘导出到系统桌面。
- 导出的文件名固定为 `备忘录.txt`。
- 导出内容会按文本形式逐条写入，便于脱离程序单独查看。

### 6. 简单信息弹窗

- 菜单栏 `关于我们` 中提供 `作者简介` 与 `项目简介` 两个入口。
- 程序内部封装了 `MyJDialog` 用于展示提示类弹窗。
- 未选中数据、输入为空、导出完成等场景也会通过弹窗反馈给用户。

## 技术栈

| 技术 / 组件              | 说明                                                            |
|----------------------|---------------------------------------------------------------|
| Java SE              | 项目核心开发语言，负责业务逻辑、文件读写与对象管理                                     |
| Swing / AWT          | 用于实现桌面图形界面、按钮、表格、菜单、弹窗等组件                                     |
| Java IO              | 负责本地文件读写、导出文本文件                                               |
| Object Serialization | 使用 `ObjectInputStream` / `ObjectOutputStream` 持久化 `Memo` 对象集合 |
| IntelliJ IDEA 工程结构   | 项目包含 `.iml` 与 `.idea` 配置，适合直接以 IDEA 工程方式打开                    |

## 核心实现思路

### 数据存储

- 备忘数据保存在 `src/data.txt`。
- 实际存储格式并不是纯文本，而是 `ArrayList<Memo>` 的序列化结果。
- 程序首次运行时，如果文件不存在或为空，会自动初始化一个空集合并写入。

### 界面组织

- `Main`：主窗口，负责列表展示、菜单操作与增删改入口。
- `Add`：新增窗口，负责录入新备忘。
- `Update`：修改窗口，负责编辑已有备忘。
- `MyJDialog`：封装通用提示弹窗。

### 数据流转

- 读取数据统一走 `General.readMemoFile()`。
- 新增操作调用 `General.saveOneMemo()`。
- 修改操作会更新目标对象后调用 `General.saveAllMemos()`。
- 删除操作调用 `General.deleteMemo()`。
- 导出操作调用 `General.download()`。

## 目录与文件说明

| 路径                                     | 说明                       |
|----------------------------------------|--------------------------|
| `src/App.java`                         | 程序启动入口，启动后直接打开主界面        |
| `src/commons/Memo.java`                | 备忘录实体类，包含标题、内容以及序列化能力    |
| `src/commons/General.java`             | 通用工具类，负责读取、保存、删除、导出备忘数据  |
| `src/uis/Main.java`                    | 主界面窗口，负责表格展示、菜单栏、按钮事件处理  |
| `src/uis/Add.java`                     | 新增备忘窗口                   |
| `src/uis/Update.java`                  | 修改备忘窗口                   |
| `src/views/MyJDialog.java`             | 自定义提示弹窗工具                |
| `src/data.txt`                         | 程序本地数据文件，保存序列化后的备忘集合     |
| `src/META-INF/MANIFEST.MF`             | JAR 清单文件，指定主类为 `App`     |
| `asserts/`                             | README 使用的项目 LOGO 与效果图资源 |
| `release/SimpleMemo V1.1.3 setup .exe` | 已打包好的 Windows 安装程序       |
| `out/`                                 | 编译输出目录                   |
| `.idea/`、`SimpleMemo.iml`              | IntelliJ IDEA 工程配置文件     |

## 运行方式

### 方式一：直接运行源码

推荐使用 IntelliJ IDEA 打开项目，然后直接运行 `src/App.java`。

运行时建议注意：

- 当前工作目录最好保持在项目根目录，否则相对路径 `src/data.txt` 可能无法正确定位。
- 项目使用的是本地文件持久化，因此不依赖数据库，也不需要额外服务。

### 方式二：使用安装包

仓库中已经提供 Windows 安装包：

- `release/SimpleMemo V1.1.3 setup .exe`

如果只是想快速体验功能，直接安装运行会更方便。

## 效果图

<div align="center">
  <table align="center">
    <tr>
      <td align="center" width="50%"><img src="asserts/1.jpg" alt="SimpleMemo Screenshot 1" width="100%" /></td>
      <td align="center" width="50%"><img src="asserts/2.jpg" alt="SimpleMemo Screenshot 2" width="100%" /></td>
    </tr>
    <tr>
      <td align="center" width="50%"><img src="asserts/3.jpg" alt="SimpleMemo Screenshot 3" width="100%" /></td>
      <td align="center" width="50%"><img src="asserts/4.jpg" alt="SimpleMemo Screenshot 4" width="100%" /></td>
    </tr>
    <tr>
      <td align="center" width="50%"><img src="asserts/5.jpg" alt="SimpleMemo Screenshot 5" width="100%" /></td>
      <td align="center" width="50%"><img src="asserts/6.jpg" alt="SimpleMemo Screenshot 6" width="100%" /></td>
    </tr>
    <tr>
      <td align="center" width="50%"><img src="asserts/7.jpg" alt="SimpleMemo Screenshot 7" width="100%" /></td>
      <td align="center" width="50%"><img src="asserts/8.jpg" alt="SimpleMemo Screenshot 8" width="100%" /></td>
    </tr>
    <tr>
      <td align="center" width="50%"><img src="asserts/9.jpg" alt="SimpleMemo Screenshot 9" width="100%" /></td>
      <td align="center" width="50%"><img src="asserts/10.jpg" alt="SimpleMemo Screenshot 10" width="100%" /></td>
    </tr>
  </table>
</div>

## 项目说明

这是一个学生时期的练手项目。更准确一点说，它是当年刚学完相关课程之后，为了熟悉 Java 基础、Swing
图形界面、事件监听、对象序列化和文件操作而写下来的一个小作品。

所以如果你在代码里看到一些不够成熟的写法、一些现在看来不太合理的实现，甚至个别
Bug，其实都很正常。这些内容我没有刻意“美化”成完全工业化的样子，而是尽量保留了那个阶段真实的学习痕迹。毕业之后再回头整理这个仓库，更多是一种阶段总结和留档。

欢迎交流，也感谢包容，不喜勿喷。

## 已知情况

基于当前实现，项目有几个比较明显的特点：

- 数据文件 `src/data.txt` 使用的是 Java 序列化格式，人眼直接打开时并不适合阅读。
- 导出路径固定为系统桌面，导出文件名固定为 `备忘录.txt`。
- 项目是典型的课程练手风格，当前没有引入 Maven / Gradle，也没有更完整的工程化配置。
- 一些交互和实现方式比较朴素，但也正因为如此，源码整体比较适合初学阶段阅读。

## License

本项目基于 [MIT License](./LICENSE.lic) 开源。
