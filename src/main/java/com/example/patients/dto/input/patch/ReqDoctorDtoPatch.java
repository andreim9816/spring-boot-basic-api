package com.example.patients.dto.input.patch;

import com.example.patients.constraint.annotation.ValidDepartmentId;
import com.example.patients.dto.PersonDto;
import lombok.Data;

@Data
public class ReqDoctorDtoPatch extends PersonDto {

    @ValidDepartmentId
    private Long departmentId;
}
