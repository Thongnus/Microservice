package com.example.servicea.DTO.Response;

import java.io.Serializable;

/**
 * DTO for {@link com.example.servicea.Model.Student}
 */
public record StudentDtoResponse(Long Id, String name, int age, int idclass) implements Serializable {
}