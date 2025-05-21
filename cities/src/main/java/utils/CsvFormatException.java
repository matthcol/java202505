package utils;

public class CsvFormatException extends RuntimeException {

    public CsvFormatException() {
        // call constructor from parent class
        super("the input is not conform to CSV standard");
    }

    public CsvFormatException(String message) {
        super(message);
    }

    public CsvFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
