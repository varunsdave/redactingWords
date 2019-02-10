package com.varunsdave;
import com.varunsdave.WordsRedactor.UnixStringFilesUtil;
import com.varunsdave.WordsRedactor.WordsRedactor;

import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Use this program to run the word extractor. The output folder will be overwritten
 * with files.
 */
public class Main {

    public static void main(String[] args) {
        if (isValidInputs(args)) {
            new WordsRedactor(
                    new UnixStringFilesUtil()
            ).run(args);
        }
    }

    private static boolean isValidInputs(String[] args) throws IllegalArgumentException{
        if (args.length < 3) {
            throw new IllegalArgumentException("You must enter 3 arguments. \n" +
                    "Argument 1: Path to a file containing readacting terms.\n" +
                    "Argument 2: Directory containing all the files to redact words from.\n" +
                    "Argument 3: Output file");
        }
        for (int i = 0; i < args.length; i++) {
            if (Files.notExists(Paths.get(args[i]))) {
               throw new IllegalArgumentException("Invalid argument: '" + args[i] + "' does not exist. System will exit.");
            }
        }
        return true;
    }
}
