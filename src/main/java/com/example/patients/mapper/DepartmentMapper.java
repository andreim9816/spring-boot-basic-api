package com.example.patients.mapper;

import com.example.patients.dto.DepartmentDto;
import com.example.patients.dto.input.ReqDepartmentDto;
import com.example.patients.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DepartmentMapper {

    DepartmentDto toDto(Department entity);

    Department toEntity(ReqDepartmentDto dto);

    Department update(ReqDepartmentDto req, @MappingTarget Department entity);
}
