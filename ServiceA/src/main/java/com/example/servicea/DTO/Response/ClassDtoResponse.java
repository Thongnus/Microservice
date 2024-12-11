package com.example.servicea.DTO.Response;

import java.io.Serializable;

/**
 * DTO for {@link com.example.servicea.Model.Classes}
 */
public record ClassDtoResponse(Long Id, String nameclass) implements Serializable {
}