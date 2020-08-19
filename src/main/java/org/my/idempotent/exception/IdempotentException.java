package org.my.idempotent.exception;

public class IdempotentException extends Exception{
    public IdempotentException(String message) {
        super(message);
    }
}
