package com.example.servicea.Exception;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

import static java.rmi.server.LogStream.log;

@RestControllerAdvice
@Slf4j
public class GlobalAdvice {
    final  static Logger logger = (Logger) LoggerFactory.getLogger(GlobalAdvice.class);
    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<?> handlerRatelimit(RequestNotPermitted e){
        logger.error(e.getMessage());
        return new ResponseEntity<>(Error.ManytoRequest.code,HttpStatus.TOO_MANY_REQUESTS);
    }

}
