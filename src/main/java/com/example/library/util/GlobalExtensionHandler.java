package com.example.library.util;

import com.raika.customexception.exceptions.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExtensionHandler extends GlobalExceptionHandler {
    public GlobalExtensionHandler() {
        super();
    }
}