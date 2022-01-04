package com.example.patients.mapper;

import com.example.patients.dto.ConsultDto;
import com.example.patients.model.Consult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ConsultMapper {

    @Mapping(source = "patient.id", target = "patientId")
    ConsultDto toDto(Consult entity);

    Consult toEntity(ConsultDto dto);
}
