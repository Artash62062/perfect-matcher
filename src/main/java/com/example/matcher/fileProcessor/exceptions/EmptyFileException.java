package com.example.matcher.fileProcessor.exceptions;

public class EmptyFileException extends RuntimeException{

    public EmptyFileException(String message) {
        super(message);
    }
}
