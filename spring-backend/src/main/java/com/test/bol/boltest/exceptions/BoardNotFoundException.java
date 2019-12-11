package com.test.bol.boltest.exceptions;

/**
 * Exception for the board not found.
 * @author Jean Carvalho
 */
public class BoardNotFoundException extends Exception {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
