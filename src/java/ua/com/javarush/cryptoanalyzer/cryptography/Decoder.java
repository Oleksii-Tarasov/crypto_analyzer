package ua.com.javarush.cryptoanalyzer.cryptography;

import ua.com.javarush.cryptoanalyzer.constants.ConsoleMessage;

import java.io.*;

import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.ALPHABET;
import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.alphabetSize;

public class Decoder {
    public void startDecryption(String inputFilePath, String outputFilePath, int encryptionKey) {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))
        ) {
            while (reader.ready()) {
                char characterFromFile = (char) reader.read();
                int indexCharacterFromAlphabet = ALPHABET.indexOf(characterFromFile);

                if (indexCharacterFromAlphabet == -1) {
                    writer.write(characterFromFile);
                }
                else {
                    int indexForDecryption = indexCharacterFromAlphabet - encryptionKey;
                    if (indexForDecryption < 0) {
                        indexForDecryption = alphabetSize + indexForDecryption;
                    }
                    writer.write(ALPHABET.get(indexForDecryption));
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
