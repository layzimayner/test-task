package com.test.task.exception;

public class NotYourArticleException extends RuntimeException {
    public NotYourArticleException(String message) {
        super(message);
    }
}
