package ua.com.javarush.cryptoanalyzer.cryptography;

import ua.com.javarush.cryptoanalyzer.constants.ConsoleMessage;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.ALPHABET;
import static ua.com.javarush.cryptoanalyzer.constants.Alphabet.alphabetSize;

public class BruteForceDecoder {
    public void startBruteForceDecryption(String inputFilePath, String outputFilePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            Map<Character, Integer> mapCharacters = new TreeMap<>();

            while (reader.ready()) {
                char characterFromFile = (char) reader.read();
                if(mapCharacters.containsKey(characterFromFile)) {
                    int charsCounter = mapCharacters.get(characterFromFile);
                    charsCounter++;
                    mapCharacters.put(characterFromFile, charsCounter);
                }
                else {
                    mapCharacters.put(characterFromFile, 1);
                }
            }

            int maxCharacterRepetition = Collections.max(mapCharacters.values());
            char maxRepeatedCharacter = ' ';

            for(Map.Entry<Character, Integer> pair : mapCharacters.entrySet()) {
                if (pair.getValue() == maxCharacterRepetition) {
                    maxRepeatedCharacter = pair.getKey();
                    break;
                }
            }

            reader = new BufferedReader(new FileReader(inputFilePath));

            while (reader.ready()){
                char characterFromFile = (char) reader.read();
                if (characterFromFile == maxRepeatedCharacter) {
                    int indexCharacterFromAlphabet = ALPHABET.indexOf(characterFromFile);
                    int indexOfSpace = ALPHABET.indexOf(' ');
                    int encryptionKey = alphabetSize - (indexOfSpace - indexCharacterFromAlphabet);
                    new Decoder().startDecryption(inputFilePath, outputFilePath, encryptionKey);
                    break;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(ConsoleMessage.FILE_NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
