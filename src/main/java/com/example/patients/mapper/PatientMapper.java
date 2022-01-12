package com.example.patients.mapper;

import com.example.patients.dto.PatientDto;
import com.example.patients.dto.input.ReqPatientDto;
import com.example.patients.dto.input.update.ReqPatientUpdateDto;
import com.example.patients.mapper.qualifier.MapperQualifier;
import com.example.patients.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = MapperQualifier.class)
public interface PatientMapper {

    @Mapping(source = "address.no", target = "address.number")
    PatientDto toDto(Patient entity);

    @Mapping(source = "depId", target = "department", qualifiedByName = "idToDepartment")
    @Mapping(source = "dto.addressId", target = "address", qualifiedByName = "idToAddress")
    Patient toEntityForCreate(Long depId, ReqPatientDto dto);

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "idToDepartment")
    @Mapping(source = "addressId", target = "address", qualifiedByName = "idToAddress")
    Patient update(ReqPatientUpdateDto req, @MappingTarget Patient entity);
}
