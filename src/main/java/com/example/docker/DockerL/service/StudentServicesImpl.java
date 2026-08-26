package com.example.docker.DockerL.service;

import com.example.docker.DockerL.dto.StudentRequestDto;
import com.example.docker.DockerL.dto.StudentResponseDto;
import com.example.docker.DockerL.entity.Student;
import com.example.docker.DockerL.exception.DuplicateEmailException;
import com.example.docker.DockerL.exception.StudentNotFoundException;
import com.example.docker.DockerL.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServicesImpl implements StudentServices {

    private final StudentRepository studentRepository;
    public static final Logger logger = (Logger) LoggerFactory.getLogger(StudentServicesImpl.class);

    public StudentServicesImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @CacheEvict(value = "students", allEntries = true)
    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {

        if (studentRepository.existsByEmail(studentRequestDto.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + studentRequestDto.getEmail());
        }

        logger.info("Creating student with email: " + studentRequestDto.getEmail());
        simulateSlowOperation();

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

    @Cacheable(value = "students")
    @Override
    public List<StudentResponseDto> getAllStudents() {

        List<Student> students = studentRepository.findAll();

        logger.info("Retrieved " + students.size() + " students from the database");
        simulateSlowOperation();

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
        }).collect(java.util.stream.Collectors.toList());
    }

    @Cacheable(value = "student", key = "#studentId")
    @Override
    public StudentResponseDto getStudentById(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        logger.info("Retrieved student with id: " + studentId);
        simulateSlowOperation();

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

    @Caching(
            put = @CachePut(value = "student", key = "#studentId"),
            evict = @CacheEvict(value = "students", allEntries = true)
    )
    @Override
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        if (studentRepository.existsByEmailAndIdNot(studentRequestDto.getEmail(), studentId)) {

            throw new DuplicateEmailException(
                    "Email " + studentRequestDto.getEmail() + " is already used by another student"
            );
        }

        logger.info("Updating student with id: " + studentId + " to have email: " + studentRequestDto.getEmail());
        simulateSlowOperation();

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

    @Caching(evict = {
            @CacheEvict(value = "student", key = "#studentId"),
            @CacheEvict(value = "students", allEntries = true)
    })
    @Override
    public void deleteStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));

        logger.info("Deleting student with id: " + studentId);
        simulateSlowOperation();

        studentRepository.delete(student);
    }

    private void simulateSlowOperation() {
        try {
            Thread.sleep(1000); // Simulate a 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
