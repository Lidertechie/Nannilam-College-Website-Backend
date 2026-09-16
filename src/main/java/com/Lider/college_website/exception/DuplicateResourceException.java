package com.Lider.college_website.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public static DuplicateResourceException forField(String entityName, String field, Object value) {
        return new DuplicateResourceException(
                String.format("%s already exists with %s: '%s'", entityName, field, value)
        );
    }
}