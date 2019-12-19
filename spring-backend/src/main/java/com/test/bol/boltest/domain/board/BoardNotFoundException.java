package com.test.bol.boltest.domain.board;

/**
 * Exception for the board not found.
 * @author Jean Carvalho
 */
public class BoardNotFoundException extends Exception {
    public BoardNotFoundException(String message) {
        super(message);
    }
}
