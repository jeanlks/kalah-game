package com.test.bol.boltest.exceptions;

/**
 * Board empty exception for circular linked list
 * @author Jean Carvalho
 */
public class BoardEmptyException extends Exception {
    public BoardEmptyException(String message) {
        super(message);
    }

    public BoardEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
