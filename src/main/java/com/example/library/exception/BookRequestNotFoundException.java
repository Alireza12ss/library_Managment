package com.example.library.exception;

public class BookRequestNotFoundException extends RuntimeException {
    public BookRequestNotFoundException(String message) {
        super(message);
    }
}

