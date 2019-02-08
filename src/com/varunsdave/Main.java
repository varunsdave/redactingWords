package com.varunsdave;

import com.varunsdave.WordsRedactor.WordsRedactor;

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
        if (args.length < 3) {
            System.out.println("You must enter 3 arguments. \n" +
                    "Argument 1: Path to a file containing readacting terms.\n" +
                    "Argument 2: Directory containing all the files to redact words from.\n" +
                    "Argument 3: Output file");
            return;
        }

        new WordsRedactor().run(args);
    }
}
