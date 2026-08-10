package com.example.docker.DockerL.exception;

public class DuplicateEmailException extends RuntimeException{

    public DuplicateEmailException() {
        super("Email already exists on server !");
    }

    public DuplicateEmailException(String message) {
        super(message);
    }
}
