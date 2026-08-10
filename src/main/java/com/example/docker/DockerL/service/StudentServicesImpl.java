package com.example.docker.DockerL.service;

import com.example.docker.DockerL.dto.StudentRequestDto;
import com.example.docker.DockerL.dto.StudentResponseDto;
import com.example.docker.DockerL.entity.Student;
import com.example.docker.DockerL.exception.DuplicateEmailException;
import com.example.docker.DockerL.exception.StudentNotFoundException;
import com.example.docker.DockerL.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServicesImpl implements StudentServices {

    private final StudentRepository studentRepository;

    public StudentServicesImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {

        if (studentRepository.existsByEmail(studentRequestDto.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + studentRequestDto.getEmail());
        }

        Student student = new Student();
        student.setFirstName(studentRequestDto.getFirstName());
        student.setLastName(studentRequestDto.getLastName());
        student.setEmail(studentRequestDto.getEmail());
        student.setDateOfBirth(studentRequestDto.getDateOfBirth());
        student.setAddress(studentRequestDto.getAddress());
        student.setGrade(studentRequestDto.getGrade());

        Student savedStudent = studentRepository.save(student);

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(savedStudent.getId());
        studentResponseDto.setFirstName(savedStudent.getFirstName());
        studentResponseDto.setLastName(savedStudent.getLastName());
        studentResponseDto.setEmail(savedStudent.getEmail());
        studentResponseDto.setDateOfBirth(savedStudent.getDateOfBirth());
        studentResponseDto.setAddress(savedStudent.getAddress());
        studentResponseDto.setGrade(savedStudent.getGrade());

        return studentResponseDto;
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> {
            StudentResponseDto studentResponseDto = new StudentResponseDto();
            studentResponseDto.setId(student.getId());
            studentResponseDto.setFirstName(student.getFirstName());
            studentResponseDto.setLastName(student.getLastName());
            studentResponseDto.setEmail(student.getEmail());
            studentResponseDto.setDateOfBirth(student.getDateOfBirth());
            studentResponseDto.setAddress(student.getAddress());
            studentResponseDto.setGrade(student.getGrade());
            return studentResponseDto;
        }).toList();
    }

    @Override
    public StudentResponseDto getStudentById(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setFirstName(student.getFirstName());
        studentResponseDto.setLastName(student.getLastName());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setDateOfBirth(student.getDateOfBirth());
        studentResponseDto.setAddress(student.getAddress());
        studentResponseDto.setGrade(student.getGrade());
        return studentResponseDto;
    }

    @Override
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        if (studentRepository.existsByEmailAndIdNot(studentRequestDto.getEmail(), studentId)) {

            throw new DuplicateEmailException(
                    "Email " + studentRequestDto.getEmail() + " is already used by another student"
            );
        }

        student.setFirstName(studentRequestDto.getFirstName());
        student.setLastName(studentRequestDto.getLastName());
        student.setEmail(studentRequestDto.getEmail());
        student.setDateOfBirth(studentRequestDto.getDateOfBirth());
        student.setAddress(studentRequestDto.getAddress());
        student.setGrade(studentRequestDto.getGrade());

        Student updatedStudent = studentRepository.save(student);

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setFirstName(updatedStudent.getFirstName());
        studentResponseDto.setLastName(updatedStudent.getLastName());
        studentResponseDto.setEmail(updatedStudent.getEmail());
        studentResponseDto.setDateOfBirth(updatedStudent.getDateOfBirth());
        studentResponseDto.setAddress(updatedStudent.getAddress());
        studentResponseDto.setGrade(updatedStudent.getGrade());
        return studentResponseDto;


    }

    @Override
    public void deleteStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        studentRepository.delete(student);
    }
}
