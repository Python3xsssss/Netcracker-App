package com.netcracker.skillstable.exception;

public class PasswordException extends RuntimeException {
    public PasswordException() {
        super("Invalid password");
    }

    public PasswordException(String message) {
        super(message);
    }
}
