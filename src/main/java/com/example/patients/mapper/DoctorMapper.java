package com.example.patients.mapper;

import com.example.patients.dto.DoctorDto;
import com.example.patients.dto.input.ReqDoctorDto;
import com.example.patients.dto.input.update.ReqDoctorUpdateDto;
import com.example.patients.mapper.qualifier.MapperQualifier;
import com.example.patients.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = MapperQualifier.class)
public interface DoctorMapper {

    DoctorDto toDto(Doctor entity);

    Doctor toEntity(ReqDoctorDto dto);

    @Mapping(source = "depId", target = "department", qualifiedByName = "idToDepartment")
    Doctor toEntityForCreate(Long depId, ReqDoctorDto dto);

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "idToDepartment")
    Doctor update(ReqDoctorUpdateDto req, @MappingTarget Doctor entity);
}
