package com.rulo.vinted.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("You do not have permission for this action");
    }

}
