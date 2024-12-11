package com.example.servicea.Service;

import com.example.servicea.Repository.OpenFeign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@Service
public class StudentService {

    @Autowired
    OpenFeign openFeign;

    public ArrayList<?>  getAllCategory(){
        return openFeign.getallCategory();
    }

    @CircuitBreaker(name = "Student", fallbackMethod = "GetCallBack")
    public ArrayList<?> get(){
        //log.error("FETCH BY OPEN FEIGN");
        System.out.println("FECT----------------------------------"+new Date());
         return   openFeign.get();
    }
    public ArrayList<?> GetCallBack(Exception ex){
        Logger log = LoggerFactory.getLogger(StudentService.class);
        log.info("-----------------------FALLBACK------------------------");
        ex.printStackTrace();
            return new ArrayList<>();
    }

}
