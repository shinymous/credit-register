package com.cred.register.exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message){super(message);}
    public InvalidDataException(String message, String field){super(String.format("%s: %s", message, field));}
}
