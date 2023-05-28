package com.enset.ebank_backend.exeption;

public class CustomerNotFoundException extends Exception  {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
