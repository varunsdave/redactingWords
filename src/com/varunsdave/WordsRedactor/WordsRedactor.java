package com.varunsdave.WordsRedactor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Runs the application taking input from Command Line
 * This does not use Apache Commons at this point, however, some command line
 * and other build in functionality can be done with Apache commons Command Line
 * and other common implementations.
 * @author Varun Dave
 */
public class WordsRedactor {
    public void run(String[] args) {
        String keyWordsListFile = args[0];
        String inputFolderPath = args[1];
        String outputFolderPath = args[2];

        Path keyWordFilePath = Paths.get(keyWordsListFile);
        Set<String> redactingWordsSet = new HashSet<String>();

        try {
            List<String> inputData = Files.readAllLines(keyWordFilePath);
            for (String s: inputData) {
                if (!redactingWordsSet.contains(s)) {
                    redactingWordsSet.add(s);
                }
            }
            System.out.println(redactingWordsSet.size());

            try (Stream<Path> filePathStream = Files.walk(Paths.get(inputFolderPath))) {
                filePathStream.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {

                        try {
                            String input = readTextFile(filePath);
                            String redactedWords = redactWords(input, redactingWordsSet);
                            Path outputPath = Paths.get(outputFolderPath + filePath.getFileName().toString());
                            writeToTextFile(outputPath, redactedWords);

                        } catch (Exception e) {

                        }


                    }
                });
            }

        } catch (Exception e) {
        }
    }


    public String readTextFile(Path fileName) throws IOException {
        String content = new String(Files.readAllBytes(fileName)).trim();
        return content;
    }

    public void writeToTextFile(Path filePath, String content) throws IOException {
        Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE);
    }


    public String redactWords(String input, Set<String> wordsToRedact) {
        Pattern p = Pattern.compile("(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)");
        String [] words = p.split(input);
        StringBuilder sb = new StringBuilder();
        for (String w: words) {
            if (wordsToRedact.contains(w.toLowerCase())) {
                sb.append("<removed " + w.length() + ">");
            } else {
                sb.append(w);
            }
        }
        return sb.toString();
    }
}
