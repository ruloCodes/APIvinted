package com.rulo.vinted.exception;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException() {
        super();
    }

    public PedidoNotFoundException(String message) {
        super(message);
    }

    public PedidoNotFoundException(long id) {
        super("Pedido not found: " + id);
    }
}