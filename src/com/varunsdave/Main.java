package com.varunsdave;
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
            new WordsRedactor().run(args);
        }
    }

    static boolean isValidInputs(String[] args) throws IllegalArgumentException {
        if (args.length < 3) {
            System.out.println("You must enter 3 arguments. \n" +
                    "Argument 1: Path to a file containing readacting terms.\n" +
                    "Argument 2: Directory containing all the files to redact words from.\n" +
                    "Argument 3: Output file");
            return false;
        }
        for (int i = 0; i < args.length; i++) {
            if (Files.notExists(Paths.get(args[i]))) {
                System.out.println("Invalid argument: '" + args[i] + "' does not exist. System will exit.");
                return false;
            }
        }
        return true;
    }
}
