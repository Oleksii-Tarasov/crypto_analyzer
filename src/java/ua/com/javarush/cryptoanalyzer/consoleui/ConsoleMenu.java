package ua.com.javarush.cryptoanalyzer.consoleui;

import ua.com.javarush.cryptoanalyzer.cryptography.BruteForceDecoder;
import ua.com.javarush.cryptoanalyzer.cryptography.Decoder;
import ua.com.javarush.cryptoanalyzer.cryptography.Encoder;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

import static ua.com.javarush.cryptoanalyzer.constants.Constants.Alphabet.ALPHABET_SIZE;
import static ua.com.javarush.cryptoanalyzer.constants.Constants.Common.DOUBLE_SLASH;
import static ua.com.javarush.cryptoanalyzer.constants.Constants.ConsoleMessages.*;
import static ua.com.javarush.cryptoanalyzer.constants.Constants.ConsoleOptions.*;

public class ConsoleMenu {
    private Scanner console = new Scanner(System.in);

    public void startDialog() {
        boolean isWorking = true;

        while (isWorking) {
            System.out.print(MENU_OPTIONS);
            String selectedOption = console.nextLine();

            if ("Q".equals(selectedOption)) {
                System.out.println(EXIT);
                console.close();
                isWorking = false;
            } else if ("E".equals(selectedOption) || "D".equals(selectedOption) || "DBF".equals(selectedOption)) {
                String inputFilePath = putInputFilePath();
                String outputFilePath = putOutputFilePath();
                int encryptionKey = 0;
                if ("E".equals(selectedOption) || "D".equals(selectedOption)) {
                    encryptionKey = putEncryptionKey();
                }
                switchStrategy(selectedOption, inputFilePath, outputFilePath, encryptionKey);
            } else {
                System.out.println(ILLEGAL_OPERATION);
            }
        }
    }

    private void switchStrategy(String selectedOption, String inputFilePath, String outputFilePath, int encryptionKey) {
        Decoder decoder = new Decoder();
        switch (selectedOption) {
            case "E" -> {
                new Encoder().startEncryption(inputFilePath, outputFilePath, encryptionKey);
                System.out.println("* File encrypted -> " + outputFilePath);
            }
            case "D" -> {
                decoder.startDecryption(inputFilePath, outputFilePath, encryptionKey);
                System.out.println("* File decrypted -> " + outputFilePath);
            }
            case "DBF" -> {
                new BruteForceDecoder(decoder).startBruteForceDecryption(inputFilePath, outputFilePath);
                System.out.println("* File decrypted with brute force -> " + outputFilePath);
            }
        }
    }

    private String putInputFilePath() {
        boolean isWorking = true;
        String inputFilePath = "";

        while (isWorking) {
            System.out.print(ENTER_INPUT_FILE);
            inputFilePath = console.nextLine();

            try {
                if (!Files.isRegularFile(Path.of(inputFilePath))) {
                    System.out.println(FILE_NOT_FOUND);
                }
                else {
                    isWorking = false;
                }
            }
            catch (InvalidPathException e) {
                System.out.println(FILE_NOT_FOUND);
            }
        }
        return inputFilePath;
    }

    private String putOutputFilePath() {
        boolean isWorking = true;
        String outputFilePath = "";

        while (isWorking) {
            System.out.print(ENTER_OUTPUT_FOLDER);
            Path folderPath = Path.of(console.nextLine());

            try {
                if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
                    System.out.print(ENTER_OUTPUT_FILE_NAME);
                    String fileName = console.nextLine() + ".txt";
                    outputFilePath = folderPath + DOUBLE_SLASH + fileName;
                    isWorking = false;
                }
                else {
                    System.out.println(DIRECTORY_NOT_FOUND);
                }
            }
            catch (InvalidPathException e) {
                System.out.println(DIRECTORY_NOT_FOUND);
            }
        }
        return outputFilePath;
    }

    private int putEncryptionKey() {
        boolean isWorking = true;
        int encryptionKey = 0;

        while (isWorking) {
            System.out.print(ENTER_ENCRYPTION_KEY);
            try {
                encryptionKey = Integer.parseInt(console.nextLine());
                if (encryptionKey <= 0 || encryptionKey >= ALPHABET_SIZE) {
                    System.out.printf("* Key should be between 0 and %d *\n", ALPHABET_SIZE);
                }
                else {
                    isWorking = false;
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_KEY);
            }
        }
        return encryptionKey;
    }
}
