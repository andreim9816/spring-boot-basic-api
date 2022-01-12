package com.example.patients.dto.input.update;

import com.example.patients.constraint.annotation.ValidDepartmentId;
import com.example.patients.dto.PersonDto;
import lombok.Data;

@Data
public class ReqDoctorUpdateDto extends PersonDto {

    @ValidDepartmentId
    private Long departmentId;
}
