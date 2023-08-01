package com.bol.kalaha.exception;

public class GameIsFullException extends RuntimeException {

    public GameIsFullException(String message) {
        super(message);
    }
}
