package com.example.library.exception;

public class DuplicateBookGroupException extends RuntimeException {
    public DuplicateBookGroupException(String message) {
        super(message);
    }
}