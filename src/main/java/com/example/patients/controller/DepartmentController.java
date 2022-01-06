package com.example.patients.controller;

import com.example.patients.dto.DepartmentDto;
import com.example.patients.dto.DoctorDto;
import com.example.patients.dto.PatientDto;
import com.example.patients.dto.input.ReqDepartmentDto;
import com.example.patients.mapper.DepartmentMapper;
import com.example.patients.mapper.DoctorMapper;
import com.example.patients.mapper.PatientMapper;
import com.example.patients.model.Department;
import com.example.patients.service.DepartmentService;
import com.example.patients.service.constraint.ValidDepartmentId;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/departments")
@Validated
@Api(value = "/departments",
        tags = "Departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;

    @Autowired
    public DepartmentController(DepartmentService departmentService, DepartmentMapper departmentMapper, DoctorMapper doctorMapper, PatientMapper patientMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
        this.doctorMapper = doctorMapper;
        this.patientMapper = patientMapper;
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all departments"
    )
    public ResponseEntity<List<DepartmentDto>> getAll() {

        List<DepartmentDto> result = departmentService.getAll()
                .stream().map(departmentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{department-id}/doctors")
    @Operation(
            method = "GET",
            summary = "Get all doctors in a department"
    )
    public ResponseEntity<List<DoctorDto>> getAllDoctorsInDepartment(@PathVariable("department-id") @ValidDepartmentId Long departmentId) {

        List<DoctorDto> result = departmentService.getAllDoctorsInDepartment(departmentId)
                .stream().map(doctorMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{department-id}/patients")
    @Operation(
            method = "GET",
            summary = "Get all patients in a department"
    )
    public ResponseEntity<List<PatientDto>> getAllPatientsInDepartment(@PathVariable("department-id") @ValidDepartmentId Long departmentId) {

        List<PatientDto> result = departmentService.getAllPatientsInDepartment(departmentId)
                .stream().map(patientMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{department-id}")
    @Operation(
            method = "GET",
            summary = "Get a department by ID"
    )
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("department-id") @ValidDepartmentId Long id) {

        DepartmentDto result = departmentMapper.toDto(departmentService.getById(id));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Save a new department"
    )
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody @Valid ReqDepartmentDto reqDepartment) {

        Department department = departmentMapper.toEntity(reqDepartment);
        Department savedDepartment = departmentService.save(department);
        DepartmentDto result = departmentMapper.toDto(savedDepartment);

        return ResponseEntity
                .created(URI.create(String.format("departments/%s", result.getId())))
                .body(result);
    }

    @PatchMapping("/{department-id}")
    @Operation(
            method = "PATCH",
            summary = "Update a department"
    )
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("department-id") @ValidDepartmentId Long departmentId,
                                                          @RequestBody @Valid ReqDepartmentDto reqDepartment) {

        Department department = departmentService.getById(departmentId);
        Department updatedDepartment = departmentMapper.update(reqDepartment, department);
        Department savedDepartment = departmentService.save(updatedDepartment);
        DepartmentDto result = departmentMapper.toDto(savedDepartment);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{department-id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a department"
    )
    public ResponseEntity<?> deleteDepartment(@PathVariable("department-id") @ValidDepartmentId Long departmentId) {

        departmentService.deleteById(departmentId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
