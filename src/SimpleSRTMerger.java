import java.io.*;
import java.util.*;

public class SimpleSRTMerger {
    public static void main(String[] args) throws IOException {

        // 从命令行参数中获取文件路径
        String firstSRTInput = args[0]; // 第一个字幕 First subtitle file
        String secondSRTInput = args[1]; // 第二个字幕 Second subtitle file
        String outputFilePath = args[2];  // 输出合并的SRT文件 Output merged SRT file path

        // 要合并的两个文件，如 List<String[]> subtitles1 = readSRTFile("X:\文件夹\字幕1.srt");
        List<String[]> subtitles1 = readSRTFile(firstSRTInput);
        List<String[]> subtitles2 = readSRTFile(secondSRTInput);
        for (String[] line : subtitles1) {
            System.out.println(Arrays.toString(line));
        }
        subtitles1 = delSRTOrder(subtitles1);
        subtitles2 = delSRTOrder(subtitles2);
        for (String[] line : subtitles1) {
            System.out.println(Arrays.toString(line));
        }
        List<String> mergedSubtitles = mergeSubtitles(subtitles1, subtitles2);
        // 将要导出的文件写在下方，如 writeMergedSRTFile("X:\文件夹\合并.srt", mergedSubtitles)
        writeMergedSRTFile(outputFilePath, mergedSubtitles);
    }
    /**
     * Read SRT file into ArrayList containing string Arrays, e.g.:
     * ArrayList<>:
     * ...
     * [7, 00:03:24,329 --> 00:03:26,247, Ah, this is pathetic !]
     * [8, 00:03:26,331 --> 00:03:28,541, Now he's a loser, with a Jaguar.]
     * [9, 00:03:28,625 --> 00:03:31,544, Seriously, who did he have to blow, to get that thing ?]
     * ...
     * @param filePath Specify to open a file
     * @return ArrayList containing String arrays, each String array is a line of SRT subtitle
     * @throws IOException When the file is missing
     */
    private static List<String[]> readSRTFile(String filePath) throws IOException {
        List<String[]> subtitles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder subtitleBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    // Process the accumulated subtitle block
                    if (!subtitleBuilder.isEmpty()) {
                        String[] subtitleBlock = subtitleBuilder.toString().trim().split("\n");
                        subtitles.add(subtitleBlock);
                        subtitleBuilder.setLength(0); // Clear the builder for the next subtitle
                    }
                }
                else { subtitleBuilder.append(line).append("\n"); }
            }
            // Process the last subtitle block if it exists
            if (!subtitleBuilder.isEmpty()) {
                String[] subtitleBlock = subtitleBuilder.toString().trim().split("\n");
                subtitles.add(subtitleBlock);
            }
        }
        return subtitles;
    }
    /**
     * Read SRT file into ArrayList containing string Arrays, e.g.:
     * ArrayList<>:
     * ...
     * [00:03:24,329 --> 00:03:26,247, Ah, this is pathetic !]
     * [00:03:26,331 --> 00:03:28,541, Now he's a loser, with a Jaguar.]
     * [00:03:28,625 --> 00:03:31,544, Seriously, who did he have to blow, to get that thing ?]
     * ...
     * @param srtList ArrayList containing String arrays, should be the output of readSRTFile
     * @return ArrayList containing String arrays, each String array is a line of SRT subtitle
     */
    private static List<String[]> delSRTOrder(List<String[]> srtList) {
        List<String[]> subtitles = new ArrayList<>();
        for (String[] line : srtList) {
            subtitles.add(Arrays.copyOfRange(line, 1, line.length));
        }
        return subtitles;
    }
    /**
     * 1. Read some lines in the first file, until a double line break (\n\n) is reached
     * ```
     * 1
     * 00:02:57,510 --> 00:03:00,054
     * I wish you wouldn't do that around me.
     * It's so filthy ! (Here is the first linebreak)
     * (Here is the second linebreak)
     * ```
     * 2. Read some lines in the second file, until a double line break (\n\n) is reached, like step 1
     * -
     * 3. Record both time stamps and order into long variable,
     * if any lines we have is empty,assign it with null or MAX_VALUE
     * to preserve order of which subtitle goes first
     * -
     * 4. If any subtitle lines exist, write subtitle order into output variable or write into output file
     * 5. Compare the two time stamps and complete a write process
     * @param subs1 First subtitle in ArrayList of string arrays
     * @param subs2 Second subtitle in ArrayList of string arrays
     * @return Merged subtitle
     */
    private static List<String> mergeSubtitles(List<String[]> subs1, List<String[]> subs2) {
        List<String> merged = new ArrayList<>();
        int i=0, j=0;

        while (i < subs1.size() || j < subs2.size()) {
            long firstSubStamp = getTimestamp(subs1, i);
            long secondSubStamp = getTimestamp(subs2, j);

            if (firstSubStamp == Long.MAX_VALUE && secondSubStamp == Long.MAX_VALUE) {
                break; // No more subtitles to process
            }

            if (firstSubStamp < secondSubStamp) {
                if (firstSubStamp != Long.MAX_VALUE) {
                    merged.add(String.join("\n", subs1.get(i++)) + "\n");
                } else { i++; }
            }
            else if (secondSubStamp < firstSubStamp) {
                if (secondSubStamp != Long.MAX_VALUE) {
                    merged.add(String.join("\n", subs2.get(j++)) + "\n");
                }
                else { j++; }
            }
            else { // Same timestamp
                if (firstSubStamp != Long.MAX_VALUE) {
                    merged.add(String.join("\n", subs1.get(i++)) + "\n");
                }
                if (secondSubStamp != Long.MAX_VALUE) {
                    merged.add(String.join("\n", subs2.get(j++)) + "\n");
                }
            }
        }
        return merged;
    }
    /**
     * Process the subtitle ArrayList of String arrays to fetch the SRT subtitle track starting timestamp
     * @param subs Subtitle to be processed
     * @param index The subtitle string array slice to work on
     * @return The time stamp converted
     */
    private static long getTimestamp(List<String[]> subs, int index) {
        if (index >= subs.size()) return Long.MAX_VALUE; // No more subtitles
        String[] subtitle = subs.get(index);
        String timeLine = subtitle[0]; // The second line contains the timestamp, becomes 1st line after delSRTOrder()
        String[] times = timeLine.split(" --> ")[0].split(":|,");
        long hours = Long.parseLong(times[0]) * 3600000; // Convert hours to milliseconds
        long minutes = Long.parseLong(times[1]) * 60000; // Convert minutes to milliseconds
        long seconds = Long.parseLong(times[2]) * 1000; // Convert seconds to milliseconds
        long milliseconds = Long.parseLong(times[3]); // Milliseconds
        return hours + minutes + seconds + milliseconds;
    }
    /**
     * Write merged SRT file to specified file path and file name
     * @param outputFilePath In Windows, it should be something like X:\folder\merged.srt
     * @param mergedSubtitles Completed ArrayList of strong arrays to be exported
     * @throws IOException When writing to this path is impossible
     */
    private static void writeMergedSRTFile(String outputFilePath, List<String> mergedSubtitles) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            int index = 1;
            for (String subtitle : mergedSubtitles) {
                bw.write(index++ + "\n");
                bw.write(subtitle);
                bw.write("\n");
            }
        }
    }
}