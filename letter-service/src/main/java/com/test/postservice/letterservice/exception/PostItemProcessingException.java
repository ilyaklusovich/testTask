package com.test.postservice.letterservice.exception;

public class PostItemProcessingException extends RuntimeException {

    public PostItemProcessingException(String message) {
        super(message);
    }

    public PostItemProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}
