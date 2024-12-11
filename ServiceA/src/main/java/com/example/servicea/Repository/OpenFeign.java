package com.example.servicea.Repository;

import com.example.servicea.DTO.Response.StudentDtoResponse;
import com.example.servicea.Model.Student;
import com.example.servicea.Service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "ServiceAPI")
public interface OpenFeign {

    @GetMapping("/api-category")
//@Retry(name = "StudentRetry")
    ArrayList<?> getallCategory();

    @GetMapping("/get")
//@Retry(name = "StudentRetry")
    ArrayList<Student> get();

    default ArrayList<?> GetCallBack(Exception ex) {
        Logger log = LoggerFactory.getLogger(StudentService.class);
        log.info("-----------------------FALLBACK------------------------");
        ex.printStackTrace();
        return new ArrayList<>();
    }}
//@Component
// class MyFeignClientFallback implements OpenFeign {
//
//    @Override
//    public ArrayList<Student> get() {
//      final Logger logger = LoggerFactory.getLogger(MyFeignClientFallback.class);
//        logger.info("LOG FALLBACK");
//        return null;
//    }
//}