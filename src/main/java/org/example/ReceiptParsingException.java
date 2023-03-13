package org.example;

public class ReceiptParsingException extends Exception {

    public static final String QUANTITY_MESSAGE = "Price ExclTax must be a positive float";
    public static final String EXCLTAX_MESSAGE = "Price ExclTax must be a positive float";
    public static final String GENERAL_MESSAGE = "Receipt Item must must be entered as : <quantity> <name> at <price>";

    public ReceiptParsingException() {
        super(GENERAL_MESSAGE);
    }
    public ReceiptParsingException(String message) {
        super(message);
    }
}
