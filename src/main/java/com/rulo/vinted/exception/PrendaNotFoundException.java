package com.rulo.vinted.exception;

public class PrendaNotFoundException extends RuntimeException {

    public PrendaNotFoundException() {
        super();
    }

    public PrendaNotFoundException(String message) {
        super(message);
    }

    public PrendaNotFoundException(long id) {
        super("Prenda not found: " + id);
    }
}