package com.example.patients.dto.input.patch;

import com.example.patients.dto.PersonDto;
import com.example.patients.service.constraint.ValidDepartmentId;
import lombok.Data;

@Data
public class ReqDoctorDtoPatch extends PersonDto {

    @ValidDepartmentId
    private Long departmentId;
}
