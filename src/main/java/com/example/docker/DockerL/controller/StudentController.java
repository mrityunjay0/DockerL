package com.example.docker.DockerL.controller;

import com.example.docker.DockerL.dto.StudentRequestDto;
import com.example.docker.DockerL.dto.StudentResponseDto;
import com.example.docker.DockerL.service.StudentServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentServices studentServices;

    public StudentController(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    // Create a new student
    @PostMapping("/create")
    public ResponseEntity<StudentResponseDto> createStudent(
            @Valid @RequestBody StudentRequestDto studentRequestDto) {

        StudentResponseDto createdStudent = studentServices.createStudent(studentRequestDto);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    // Get all students
    @GetMapping("/all")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {

        List<StudentResponseDto> students = studentServices.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get a student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long studentId) {

        StudentResponseDto student = studentServices.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    // Update a student
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @Valid @RequestBody StudentRequestDto studentRequestDto, @PathVariable Long studentId) {

        StudentResponseDto updatedStudent = studentServices.updateStudent(studentRequestDto, studentId);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    // Delete a student
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {

        studentServices.deleteStudent(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
