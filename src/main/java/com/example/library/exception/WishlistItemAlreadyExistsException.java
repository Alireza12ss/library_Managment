package com.example.library.exception;

public class WishlistItemAlreadyExistsException extends RuntimeException {
    public WishlistItemAlreadyExistsException(String message) {
        super(message);
    }
}