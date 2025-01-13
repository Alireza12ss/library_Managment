package com.example.library.exception;

public class WishlistItemNotFoundException extends RuntimeException {
    public WishlistItemNotFoundException(String message) {
        super(message);
    }
}