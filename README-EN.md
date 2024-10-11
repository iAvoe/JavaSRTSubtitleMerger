# Purpose

This program could merge two specified SRT subtitle files (plain text) into one. For example, assume you want to create a dual-language track:

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

This program would be able to merge the 2 SRT files above organically into `Merged.srt`

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

# Usage - Java Runtime Environment

1. Install and test java installation
  - `java -version`
2. Download `out/MergeSRTFiles.jar` and record its path, as well as the 2 SRT files to merge
3. Specify merged file output path to file, build command line with content from last step as follows:
  - `java -jar MergeSRTFiles.jar firstSRT.srt secondSRT.srt mergedFile.srt`

# Usage - Java Development Kit Environment

This project is developed in [Liberica Standard JDK 21](https://bell-sw.com/pages/downloads/#jdk-21-lts), with [Portable Intellij community edition](https://github.com/portapps/intellij-idea-community-portable). You may replace the variable for both input to your 2 SRT subttile files, then replace output variable to desired path to file, and run it.

You may run this project with javac, or other java environments that allows you to execute, and text editor to modify `main.java`. You may select `Trust this project` to be able to execute this porject normally.

This project has included `file1.srt` and `file2.srt` in the `./src/` path for demonstration purposes.