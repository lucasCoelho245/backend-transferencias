package br.com.tokyomarine.transferencias.exception;

public class TransferenciaInvalidaException extends RuntimeException {
    public TransferenciaInvalidaException(String message) {
        super(message);
    }
}