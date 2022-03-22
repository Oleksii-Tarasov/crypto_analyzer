package ua.com.javarush.cryptoanalyzer.consoleui;

import ua.com.javarush.cryptoanalyzer.constants.ConsoleMessage;
import ua.com.javarush.cryptoanalyzer.constants.ConsoleOption;
import ua.com.javarush.cryptoanalyzer.cryptography.BruteForceDecoder;
import ua.com.javarush.cryptoanalyzer.cryptography.Decoder;
import ua.com.javarush.cryptoanalyzer.cryptography.Encoder;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.alphabetSize;

public class ConsoleMenu {
    private Scanner console = new Scanner(System.in);
    private String inputFilePath;
    private String outputFilePath;
    private int encryptionKey;

    public void startDialog() {
        boolean isWorking = true;

        while (isWorking) {
            System.out.print(ConsoleOption.MENU_OPTIONS);
            String selectedOption = console.nextLine();

            if (selectedOption.equals("Q")) {
                System.out.println(ConsoleMessage.EXIT);
                console.close();
                isWorking = false;
            } else if (selectedOption.equals("E") || selectedOption.equals("D") || selectedOption.equals("DBF")) {
                putInputFilePath();
                putOutputFilePath();
                if (selectedOption.equals("E") || selectedOption.equals("D")) {
                    putEncryptionKey();
                }
                switchStrategy(selectedOption);
            } else {
                System.out.println(ConsoleMessage.ILLEGAL_OPERATION);
            }
        }
    }

    private void switchStrategy(String selectedOption) {
        switch (selectedOption) {
            case "E" -> {
                new Encoder().startEncryption(inputFilePath, outputFilePath, encryptionKey);
                System.out.println("* File encrypted -> " + outputFilePath);
            }
            case "D" -> {
                new Decoder().startDecryption(inputFilePath, outputFilePath, encryptionKey);
                System.out.println("* File decrypted -> " + outputFilePath);
            }
            case "DBF" -> {
                new BruteForceDecoder().startBruteForceDecryption(inputFilePath, outputFilePath);
                System.out.println("* File decrypted with brute force -> " + outputFilePath);
            }
        }
    }

    private void putInputFilePath() {
        boolean isWorking = true;

        while (isWorking) {
            System.out.print(ConsoleOption.ENTER_INPUT_FILE);
            inputFilePath = console.nextLine();

            try {
                if (!Files.isRegularFile(Path.of(inputFilePath))) {
                    System.out.println(ConsoleMessage.FILE_NOT_FOUND);
                }
                else {
                    isWorking = false;
                }
            }
            catch (InvalidPathException e) {
                System.out.println(ConsoleMessage.FILE_NOT_FOUND);
            }
        }
    }

    private void putOutputFilePath() {
        boolean isWorking = true;

        while (isWorking) {
            System.out.print(ConsoleOption.ENTER_OUTPUT_FOLDER);
            Path folderPath = Path.of(console.nextLine());

            try {
                if (Files.exists(folderPath) && Files.isDirectory(folderPath)) {
                    System.out.print(ConsoleOption.ENTER_OUTPUT_FILE_NAME);
                    String fileName = console.nextLine() + ".txt";
                    outputFilePath = folderPath + "\\" + fileName;
                    isWorking = false;
                }
                else {
                    System.out.println(ConsoleMessage.DIRECTORY_NOT_FOUND);
                }
            }
            catch (InvalidPathException e) {
                System.out.println(ConsoleMessage.DIRECTORY_NOT_FOUND);
            }
        }
    }

    private void putEncryptionKey() {
        boolean isWorking = true;

        while (isWorking) {
            System.out.print(ConsoleOption.ENTER_ENCRYPTION_KEY);
            try {
                encryptionKey = Integer.parseInt(console.nextLine());
                if (encryptionKey <= 0 || encryptionKey >= alphabetSize) {
                    System.out.printf("* Key should be between 0 and %d *\n", alphabetSize);
                }
                else {
                    isWorking = false;
                }
            } catch (NumberFormatException e) {
                System.out.println(ConsoleMessage.INVALID_KEY);
            }
        }
    }
}