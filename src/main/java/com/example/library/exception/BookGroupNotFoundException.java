package com.example.library.exception;

public class BookGroupNotFoundException extends RuntimeException {
    public BookGroupNotFoundException(String message) {
        super(message);
    }
}