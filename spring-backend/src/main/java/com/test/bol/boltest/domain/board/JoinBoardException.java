package com.test.bol.boltest.domain.board; 
/**
 * Exception when joining board
 * @author Jean Carvalho
 */
public class JoinBoardException extends RuntimeException {
    public JoinBoardException(String message) {
        super(message);
    }

    public JoinBoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
