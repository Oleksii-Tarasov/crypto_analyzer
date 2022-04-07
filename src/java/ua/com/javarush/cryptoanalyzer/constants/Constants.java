package ua.com.javarush.cryptoanalyzer.constants;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    private Constants() {
    }

    public static class Alphabet {
        public static final List<Character> ALPHABET = Arrays.asList('а', 'б', 'в',
                'г', 'д', 'е', 'ж', 'з', 'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у',
                'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»',
                ':', '!', '?', ' ');

        public static final int ALPHABET_SIZE = ALPHABET.size();
    }

    public static class ConsoleOptions {
        public static final String ENTER_OPTIONS = "Choose option: ";
//        public static final String MENU_OPTIONS = "E - Encoder; D - Decoder; DBF - Decoder with brute force; Q - Exit\n" +
//                "Choose option: ";
        public static final String ENTER_INPUT_FILE = "Choose input file: ";
        public static final String ENTER_OUTPUT_FILE_NAME = "Choose output file name: ";
        public static final String ENTER_OUTPUT_FOLDER = "Choose output folder: ";
        public static final String ENTER_ENCRYPTION_KEY = "Enter the encryption key: ";
    }

    public static class ConsoleMessages {
        public static final String GREETINGS =
                """
                ********************************
                ** Welcome to crypto-analyzer **
                ********************************    
                """;
        public static final String EXIT = "*** Exit from application ***";
        public static final String ILLEGAL_OPERATION = "* Illegal operation *";
        public static final String INVALID_KEY = "* Invalid encryption key *";
        public static final String FILE_NOT_FOUND = "* File not found *";
        public static final String DIRECTORY_NOT_FOUND = "* Directory not found *";
        public static final String ERROR_READ_FILE = "* Unable to read file *";
        public static final String FILE_ENCRYPTED = "* File encrypted -> ";
        public static final String FILE_DECRYPTED = "* File decrypted -> ";

    }

    public static class Common {
        public static final String DOUBLE_SLASH = "\\";
        public static final String DASH = " - ";
    }
}
