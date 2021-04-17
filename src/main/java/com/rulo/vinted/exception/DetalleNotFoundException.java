package com.rulo.vinted.exception;

public class DetalleNotFoundException extends RuntimeException {

    public DetalleNotFoundException() {
        super();
    }

    public DetalleNotFoundException(String message) {
        super(message);
    }

    public DetalleNotFoundException(long id) {
        super("Detail not found: " + id);
    }
}