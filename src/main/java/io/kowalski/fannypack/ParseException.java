package io.kowalski.fannypack;

class ParseException extends RuntimeException {

    ParseException(String reason, Exception cause) {
        super(reason, cause);
    }

    ParseException(String filename, int lineNum, String expected, String saw) {
        super(String.format("%s could not be parsed.\n\t[LINE %d]: %s\n\tSaw: %s", filename, lineNum, expected, saw));
    }

}