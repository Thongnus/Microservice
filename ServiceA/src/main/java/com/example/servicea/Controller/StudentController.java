package com.example.servicea.Controller;

import com.example.servicea.DTO.Response.StudentDtoResponse;
import com.example.servicea.Model.Student;
import com.example.servicea.Repository.StudentRepository;
import com.example.servicea.Service.StudentService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;
    @GetMapping("/get")
    public ArrayList<Student> getall () throws InterruptedException {
        Thread.sleep(4000);
        //    throw new RuntimeException();
       return (ArrayList<Student>) studentRepository.findAll();
    }

    @RateLimiter(name = "Student" )
    @GetMapping("h")
    public ArrayList<?> g(){
        return studentService.get();
    }


    @GetMapping("getAllCategory")
    public ArrayList<?> get(){
        return studentService.getAllCategory();
    }
}
