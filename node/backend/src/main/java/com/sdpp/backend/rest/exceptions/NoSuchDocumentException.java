package com.sdpp.backend.rest.exceptions;

public class NoSuchDocumentException extends RuntimeException {

    public NoSuchDocumentException(){
        super();
    }

    public NoSuchDocumentException(String message) {
        super(message);
    }

    public NoSuchDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
