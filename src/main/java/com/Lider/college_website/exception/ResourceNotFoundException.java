package com.Lider.college_website.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forEntity(String entityName, String field, Object value) {
        return new ResourceNotFoundException(
                String.format("%s not found with %s: '%s'", entityName, field, value)
        );
    }
}