package com.example.patients.mapper;

import com.example.patients.dto.MedicationDto;
import com.example.patients.dto.input.ReqDepartmentDto;
import com.example.patients.dto.input.ReqMedicationDto;
import com.example.patients.model.Department;
import com.example.patients.model.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MedicationMapper {

    MedicationDto toDto(Medication entity);

    Medication toEntity(ReqMedicationDto dto);

    Medication update(ReqMedicationDto req, @MappingTarget Medication entity);
}