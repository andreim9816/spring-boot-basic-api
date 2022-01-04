package com.example.patients.mapper.qualifier;

import com.example.patients.model.Department;
import com.example.patients.service.DepartmentService;
import com.example.patients.service.DoctorService;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperQualifier {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @Autowired
    public MapperQualifier(DoctorService doctorService, DepartmentService departmentService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
    }

    @Named("idToDepartment")
    public Department idToDepartment(Long id) {
        return departmentService.getById(id);
    }

}
