package com.varunsdave;
import com.varunsdave.WordsRedactor.WordsRedactor;


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
