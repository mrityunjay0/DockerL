package com.example.docker.DockerL.service;

import com.example.docker.DockerL.dto.StudentRequestDto;
import com.example.docker.DockerL.dto.StudentResponseDto;
import com.example.docker.DockerL.entity.Student;

import java.util.List;

public interface StudentServices {

    // create student
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto);

    // get all students
    public List<StudentResponseDto> getAllStudents();

    // get student by id
    public StudentResponseDto getStudentById(Long studentId);

    // update student
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long studentId);

    // delete student
    public void deleteStudent(Long studentId);
}
