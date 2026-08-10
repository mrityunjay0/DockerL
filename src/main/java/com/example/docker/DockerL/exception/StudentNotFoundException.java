package com.example.docker.DockerL.exception;

public class StudentNotFoundException extends RuntimeException{

    public StudentNotFoundException() {
        super("Student not found on server !");
    }

    public StudentNotFoundException(String message) {
        super(message);
    }
}
