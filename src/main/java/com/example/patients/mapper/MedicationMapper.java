package com.example.patients.mapper;

import com.example.patients.dto.MedicationDto;
import com.example.patients.model.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MedicationMapper {

    MedicationDto toDto(Medication entity);

    Medication toEntity(MedicationDto dto);
}
