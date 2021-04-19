package com.rulo.vinted.exception;

public class UserMissingDataException extends RuntimeException {

    public UserMissingDataException() {
        super("Please fill in the required fields");
    }

}
