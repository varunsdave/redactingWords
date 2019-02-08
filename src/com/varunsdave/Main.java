package com.varunsdave;

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

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println(args.length);
        if (args.length < 3) {
            System.out.println("You must enter 3 arguments. \n" +
                    "Argument 1: Path to a file containing readacting terms.\n" +
                    "Argument 2: Directory containing all the files to redact words from.\n" +
                    "Argument 3: Output file");
            return;
        }
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

    public static String readTextFile(Path fileName) throws IOException {
        String content = new String(Files.readAllBytes(fileName)).trim();
        return content;
    }

    public static void writeToTextFile(Path filePath, String content) throws IOException {
        Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE);
    }


    public static String redactWords(String input, Set<String> wordsToRedact) {
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
