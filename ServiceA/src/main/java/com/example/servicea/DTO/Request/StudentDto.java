package com.example.servicea.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.servicea.Model.Student}
 */
@Value
public class StudentDto implements Serializable {
    @NotNull
    Long Id;
    String name;
    int age;
    int idclass;
}