package com.example.servicea.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Error {
  ManytoRequest("01","Many to Request");
;
 public final  String code;
 public  final String message;
    Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
