package com.varunsdave.WordsRedactor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

interface FilesUtility<T> {
    public List<T> readFileAsLines(Path path);
    public T readFile(Path path) throws IOException;
    public void writeFile(Path filePath, String content) throws IOException;
    public void createFolder(Path folderPath) throws IOException;
}
