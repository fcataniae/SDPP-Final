package com.sdpp.balancer.socket.exceptions;

public class SearchExecutionException extends RuntimeException{

    public SearchExecutionException() {
        super();
    }

    public SearchExecutionException(String message) {
        super(message);
    }

    public SearchExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
