package com.rulo.vinted.exception;

public class UserDuplicateException extends RuntimeException {

    public UserDuplicateException() {
        super();
    }

    public UserDuplicateException(String email) {
        super("User already registered: " + email);
    }
}