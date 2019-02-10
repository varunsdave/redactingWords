package com.varunsdave.WordsRedactor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    FilesUtility<String> filesUtility;
    public WordsRedactor(FilesUtility<String> fileUtility) {
        this.filesUtility = fileUtility;
    }

    private Pattern wordPattern = Pattern.compile("(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)");
    public void run(String[] args) {
        String keyWordsListFile = args[0];
        String inputFolderPath = args[1];
        String outputFolderPath = args[2];


        Path keyWordFilePath = Paths.get(keyWordsListFile);


        Set<String> redactingWordsSet = WordsLookupTable
                .generateReactingWordsSet(
                        this.filesUtility.readFileAsLines(keyWordFilePath));
        try (Stream<Path> filePathStream = Files.walk(Paths.get(inputFolderPath))) {
            filePathStream.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    String redactedFileContent = redactWords(readTextFile(filePath), redactingWordsSet);
                    writeToTextFile(outputFolderPath, filePath.getFileName().toString(), redactedFileContent);
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * Attempts to read in text file based on a given path using a
     * unix string files util.
     *
     * It can be extended to use a build in filesUtil class as a dependency
     * instead
     * @param fileName
     * @return
     */
    public String readTextFile(Path fileName) {
        try {
            return this.filesUtility.readFile(fileName);
        } catch (Exception e) {
            return null;
        }

    }

    public void writeToTextFile(String outputFolderPath, String fileName, String content) {
        if (content == null || content.length() < 1) {
            System.out.println("The redactor produced no data for file, " + fileName);
            return;
        }
        try {
            Path outputPath = Paths.get(outputFolderPath + fileName);
            this.filesUtility.writeFile(outputPath, content);
        } catch (Exception e) {
            System.out.println("File: " + fileName + " was not written. Error:"  + e.getMessage());
        }

    }


    public String redactWords(String input, Set<String> wordsToRedact) {
//        Pattern p = Pattern.compile("(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)");
        String [] words = wordPattern.split(input);
        StringBuilder sb = new StringBuilder();
        for (String word: words) {
            if (wordsToRedact.contains(word.toLowerCase())) {
                sb.append("<removed " + word.length() + ">");
            } else {
                sb.append(word);
            }
        }
        return sb.toString();
    }
}
