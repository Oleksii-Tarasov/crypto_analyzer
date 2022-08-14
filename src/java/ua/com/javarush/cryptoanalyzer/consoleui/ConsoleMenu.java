package ua.com.javarush.cryptoanalyzer.consoleui;

import ua.com.javarush.cryptoanalyzer.cryptography.BruteForceDecoder;
import ua.com.javarush.cryptoanalyzer.cryptography.Decoder;
import ua.com.javarush.cryptoanalyzer.cryptography.Encoder;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Scanner;

import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.ALPHABET_SIZE;
import static ua.com.javarush.cryptoanalyzer.constants.Common.DASH;
import static ua.com.javarush.cryptoanalyzer.constants.Common.DOUBLE_SLASH;
import static ua.com.javarush.cryptoanalyzer.constants.ConsoleMessage.*;
import static ua.com.javarush.cryptoanalyzer.constants.ConsoleOption.*;

public class ConsoleMenu {
    private final Scanner console = new Scanner(System.in);

    public void startDialog() {
        System.out.println(GREETINGS);
        boolean isWorking = true;

        while (isWorking) {
            for (Operation operation : Operation.values()) {
                System.out.println(operation.getOperationToken() + DASH + operation.getDescription());
            }
            System.out.print(ENTER_OPTIONS);
            String selectedOption = console.nextLine();

            try {
                boolean withBruteForce = true;
                switch (Operation.getOperationByToken(selectedOption)) {
                    case EXIT -> isWorking = processExit();
                    case ENCODER -> processEncryption();
                    case DECODER -> processDecryption(!withBruteForce);
                    case BRUTEFORCE -> processDecryption(withBruteForce);
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean processExit() {
        System.out.println(EXIT);
        console.close();
        return false;
    }

    private void processEncryption() {
        String inputFilePath = enterInputFilePath();
        String outputFilePath = enterOutputFilePath();
        int encryptionKey = enterEncryptionKey();

        try {
            new Encoder().startEncryption(inputFilePath, outputFilePath , encryptionKey);
            System.out.println(FILE_ENCRYPTED + outputFilePath);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void processDecryption(boolean withBruteForce) {
        String inputFilePath = enterInputFilePath();
        String outputFilePath = enterOutputFilePath();
        int encryptionKey;

        if (!withBruteForce) {
            encryptionKey = enterEncryptionKey();
        }
        else {
            encryptionKey = BruteForceDecoder.calculatingEncryptionKey(inputFilePath);
        }

        try {
            new Decoder().startDecryption(inputFilePath, outputFilePath, encryptionKey);
            System.out.println(FILE_DECRYPTED + outputFilePath);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String enterInputFilePath() {
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

    private String enterOutputFilePath() {
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

    private int enterEncryptionKey() {
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
