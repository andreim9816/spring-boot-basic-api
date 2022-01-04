package com.example.patients.mapper;

import com.example.patients.dto.PatientDto;
import com.example.patients.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PatientMapper {

    PatientDto toDto(Patient entity);
}
