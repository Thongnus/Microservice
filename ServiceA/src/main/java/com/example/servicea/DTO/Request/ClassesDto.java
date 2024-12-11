package com.example.servicea.DTO.Request;

import com.example.servicea.Model.Classes;

import java.io.Serializable;

/**
 * DTO for {@link Classes}
 */
public record ClassesDto(Long Id, String nameclass) implements Serializable {
}