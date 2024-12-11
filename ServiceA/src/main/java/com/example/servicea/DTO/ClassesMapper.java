package com.example.servicea.DTO;

import com.example.servicea.DTO.Request.ClassesDto;
import com.example.servicea.DTO.Response.ClassDtoResponse;
import com.example.servicea.Model.Classes;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClassesMapper {
    Classes toEntity(ClassesDto classesDto);

    ClassesDto toDto(Classes classes);
    Classes EntityfromResponse(ClassDtoResponse classesDto);

    ClassDtoResponse ResponsefromEntity(Classes classes);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Classes partialUpdate(ClassesDto classesDto, @MappingTarget Classes classes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Classes partialUpdate(ClassDtoResponse classDtoResponse, @MappingTarget Classes classes);
}