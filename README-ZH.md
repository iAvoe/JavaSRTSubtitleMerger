# 用途

基本情况下，用于将两个指定的 SRT 字幕文件（纯文本）合并为一个。例如合并两种语言以创建双语轨道：

```
...
70
00:05:41,810 --> 00:05:43,340
在百慕大过复活节

71
00:05:43,710 --> 00:05:44,680
周末去看肯塔基赛马会

72
00:05:44,680 --> 00:05:46,400
可以带上你  伙计

73
00:05:47,250 --> 00:05:50,890
去那个白雪皑皑的佛蒙特得花多少钱

74
00:05:51,560 --> 00:05:52,860
一千二
...
```
<p align="center">file1.srt</p>

```
...
51
00:05:42,091 --> 00:05:45,261
Easter in Bermuda,
then Kentucky Derby weekend.

52
00:05:45,345 --> 00:05:47,472
We could fit you in, kid.

53
00:05:47,514 --> 00:05:51,392
Well, how much are
these white-bosomed slopes of Vermont ?

54
00:05:51,476 --> 00:05:53,394
Twelve hundred !
...
```
<p align="center">file2.srt</p>

接着将上述两个 SRT 文件合并为 `Merged.srt`：

```
...
120
00:05:41,810 --> 00:05:43,340
在百慕大过复活节

121
00:05:42,091 --> 00:05:45,261
Easter in Bermuda,
then Kentucky Derby weekend.

122
00:05:43,710 --> 00:05:44,680
周末去看肯塔基赛马会

123
00:05:44,680 --> 00:05:46,400
可以带上你  伙计

124
00:05:45,345 --> 00:05:47,472
We could fit you in, kid.
...
```
<p align="center">Merged.srt</p>

# 用法 - Java 运行环境

1. 安装并测试 Java 环境：
  - `java -version`
2. 下载 `out/MergeSRTFiles.jar` 并记下其路径，以及两份字幕的路径到文件
3. 指定导出的路径到文件名，与上步内容组合为命令行并运行：
  - `java -jar MergeSRTFiles.jar firstSRT.srt secondSRT.srt mergedFile.srt`

# 用法 - Java 开发环境

本项目使用了 [Liberica Standard JDK 21](https://bell-sw.com/pages/downloads/#jdk-21-lts) 环境，以及 [Portable Intellij community edition](https://github.com/portapps/intellij-idea-community-portable) 工具开发。你也可以选用 javac 等工具运行，文本编辑器修改本项目中的 `main.java`。

将最开始的两个输入文件变量改为需要合并字幕 1, 2 的路径，然后指定一个输入字幕文件的路径即可运行。你可能需要选择  `Trust this project / 信任此项目` 以正常运行。

本项目的 `./src/` 路径中包含了演示用的 `file1.srt` 和 `file2.srt`。