package com.example.docker.DockerL.config;

import com.example.docker.DockerL.entity.Student;
import com.example.docker.DockerL.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(StudentRepository studentRepository) {

        return args -> {

            // Prevent duplicate data every time the application starts
            if (studentRepository.count() > 0) {
                return;
            }

            Student student1 = new Student();
            student1.setFirstName("Rahul");
            student1.setLastName("Sharma");
            student1.setEmail("rahul.sharma@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student1.setAddress("Pune, Maharashtra");
            student1.setGrade(1);

            Student student2 = new Student();
            student2.setFirstName("Priya");
            student2.setLastName("Singh");
            student2.setEmail("priya.singh@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student2.setAddress("Bangalore, Karnataka");
            student2.setGrade(2);

            Student student3 = new Student();
            student3.setFirstName("Amit");
            student3.setLastName("Kumar");
            student3.setEmail("amit.kumar@gmail.com");
            student3.setDateOfBirth(new Date(2003, 02, 10));
            student3.setAddress("Delhi, India");
            student3.setGrade(3);

            Student student4 = new Student();
            student4.setFirstName("Sneha");
            student4.setLastName("Verma");
            student4.setEmail("sneha.verma@gmail.com");
            student4.setDateOfBirth(new Date(2002, 11, 5));
            student4.setAddress("Mumbai, Maharashtra");
            student4.setGrade(4);

            Student student5 = new Student();
            student5.setFirstName("Arjun");
            student5.setLastName("Patel");
            student5.setEmail("arjun.patel@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student5.setAddress("Ahmedabad, Gujarat");
            student5.setGrade(5);

            Student student6 = new Student();
            student6.setFirstName("Ananya");
            student6.setLastName("Reddy");
            student6.setEmail("ananya.reddy@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student6.setAddress("Hyderabad, Telangana");
            student6.setGrade(6);

            Student student7 = new Student();
            student7.setFirstName("Vikash");
            student7.setLastName("Mishra");
            student7.setEmail("vikash.mishra@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student7.setAddress("Patna, Bihar");
            student7.setGrade(7);

            Student student8 = new Student();
            student8.setFirstName("Neha");
            student8.setLastName("Gupta");
            student8.setEmail("neha.gupta@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student8.setAddress("Jaipur, Rajasthan");
            student8.setGrade(8);

            Student student9 = new Student();
            student9.setFirstName("Rohan");
            student9.setLastName("Mehta");
            student9.setEmail("rohan.mehta@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student9.setAddress("Surat, Gujarat");
            student9.setGrade(9);

            Student student10 = new Student();
            student10.setFirstName("Kavya");
            student10.setLastName("Nair");
            student10.setEmail("kavya.nair@gmail.com");
            student1.setDateOfBirth(new Date(2000,05,15));
            student10.setAddress("Kochi, Kerala");
            student10.setGrade(10);

            studentRepository.save(student1);
            studentRepository.save(student2);
            studentRepository.save(student3);
            studentRepository.save(student4);
            studentRepository.save(student5);
            studentRepository.save(student6);
            studentRepository.save(student7);
            studentRepository.save(student8);
            studentRepository.save(student9);
            studentRepository.save(student10);

            System.out.println("Sample student data inserted successfully!");
        };
    }
}