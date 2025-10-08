package com.pm.organizationservice.exception;

public class DuplicateRegistryNumberException extends RuntimeException {
    public DuplicateRegistryNumberException(String message) {
        super(message);
    }
}
