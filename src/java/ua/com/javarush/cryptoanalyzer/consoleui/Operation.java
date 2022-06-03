package ua.com.javarush.cryptoanalyzer.consoleui;

import static ua.com.javarush.cryptoanalyzer.constants.ConsoleMessages.ILLEGAL_OPERATION;

public enum Operation {
    EXIT("Q", "Exit"),
    ENCODER("E", "Encoder"),
    DECODER("D", "Decoder"),
    BRUTEFORCE("DBF", "Decoder with brute force");

    private final String operationToken;
    private final String description;

    Operation(String operationToken, String description) {
        this.operationToken = operationToken;
        this.description = description;
    }

    public String getOperationToken() {
        return operationToken;
    }

    public String getDescription() {
        return description;
    }

    public static Operation getOperationByToken(String token) {
        for (Operation operation : values()) {
            if (operation.getOperationToken().equals(token)) {
                return operation;
            }
        }
        throw new IllegalArgumentException(ILLEGAL_OPERATION);
    }
}
