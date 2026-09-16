package com.Lider.college_website.exception;

public class AccountLockedException extends RuntimeException {

    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException() {
        super("Account is locked. Please contact support.");
    }
}