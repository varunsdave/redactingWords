package com.varunsdave.WordsRedactor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Local string files utility that can be used for incoming specified string format.
 * Use this for UTF-8 and UNIX file encodings. It is the default one assumed.
 *
 */
public class UnixStringFilesUtil implements FilesUtility {

    /**
     * Reads in a file path and returns a list of lines. Will log an error for a given file.
     * This can be reused for multiple files, so the whole program doesn't need to stop.
     * @param path: Provide the relative path to the file.
     * @return
     */
    public List<String> readFileAsLines(Path path) {
        try {
            List<String> inputData = Files.readAllLines(path);
            return inputData;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public String readFile(Path path) throws IOException {
        String content = new String(Files.readAllBytes(path)).trim();
        return content;
    }

    /**
     * This file will make a folder relative to the program execution and write to it.
     *
     * @param filePath: The relative path where the files should be outputted.
     * @param content
     * @throws IOException
     */
    public void writeFile(Path filePath, String content) throws IOException {
        Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE);
    }

    public void createFolder(Path folderPath) throws IOException {

    }
}
