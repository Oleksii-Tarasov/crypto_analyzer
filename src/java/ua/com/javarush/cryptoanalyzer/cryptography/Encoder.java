package ua.com.javarush.cryptoanalyzer.cryptography;

import ua.com.javarush.cryptoanalyzer.constants.ConsoleMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.ALPHABET;
import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.alphabetSize;

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
                    if (indexForEncryption >= alphabetSize) {
                        indexForEncryption = indexForEncryption - alphabetSize;
                    }
                    writer.write(ALPHABET.get(indexForEncryption));
                }
            }
            writer.flush();

        } catch (FileNotFoundException e) {
            System.out.println(ConsoleMessage.FILE_NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}