package ua.com.javarush.cryptoanalyzer.cryptography;

import java.io.*;

import static ua.com.javarush.cryptoanalyzer.constants.Alphabets.ALPHABET;
import static ua.com.javarush.cryptoanalyzer.constants.Alphabets.ALPHABET_SIZE;
import static ua.com.javarush.cryptoanalyzer.constants.ConsoleMessages.ERROR_READ_FILE;
import static ua.com.javarush.cryptoanalyzer.constants.ConsoleMessages.FILE_NOT_FOUND;

public class Encoder {

    public void startEncryption(String inputFilePath, String outputFilePath, int encryptionKey) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))
        ) {
            while (reader.ready()) {
                char characterFromFile = (char) reader.read();

                int indexCharacterFromAlphabet = ALPHABET.indexOf(characterFromFile);
                if (indexCharacterFromAlphabet == -1) {
                    writer.write(characterFromFile);
                }
                else {
                    int indexForEncryption = indexCharacterFromAlphabet + encryptionKey;
                    if (indexForEncryption >= ALPHABET_SIZE) {
                        indexForEncryption = indexForEncryption - ALPHABET_SIZE;
                    }
                    writer.write(ALPHABET.get(indexForEncryption));
                }
            }
            writer.flush();

        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND);
        } catch (IOException e) {
            System.out.println(ERROR_READ_FILE);
        }
    }
}
