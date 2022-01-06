package com.example.patients.controller;

import com.example.patients.dto.ConsultDto;
import com.example.patients.dto.DoctorDto;
import com.example.patients.dto.input.ReqDoctorDto;
import com.example.patients.dto.input.patch.ReqDoctorDtoPatch;
import com.example.patients.mapper.ConsultMapper;
import com.example.patients.mapper.DoctorMapper;
import com.example.patients.model.Doctor;
import com.example.patients.service.DoctorService;
import com.example.patients.service.constraint.ValidDepartmentId;
import com.example.patients.service.constraint.ValidDoctor;
import com.example.patients.service.constraint.ValidPatient;
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
@RequestMapping(value = "/doctors")
@Validated
@Api(value = "/doctors",
        tags = "Doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final ConsultMapper consultMapper;

    @Autowired
    public DoctorController(DoctorService doctorService, DoctorMapper doctorMapper, ConsultMapper consultMapper) {
        this.doctorService = doctorService;
        this.doctorMapper = doctorMapper;
        this.consultMapper = consultMapper;
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all doctors"
    )
    public ResponseEntity<List<DoctorDto>> getAll() {

        List<DoctorDto> result = doctorService.getAll()
                .stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{doctor-id}/consults")
    @Operation(
            method = "GET",
            summary = "Get all consultations for a doctor"
    )
    public ResponseEntity<List<ConsultDto>> getAll(@PathVariable("doctor-id") @ValidPatient Long doctorId) {

        List<ConsultDto> result = doctorService.getById(doctorId)
                .getConsults()
                .stream()
                .map(consultMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{doctor-id}")
    @Operation(
            method = "GET",
            summary = "Get a doctor by ID"
    )
    public ResponseEntity<DoctorDto> getById(@PathVariable("doctor-id") @ValidDoctor Long id) {

        DoctorDto result = doctorMapper.toDto(doctorService.getById(id));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/{department-id}")
    @Operation(
            method = "POST",
            summary = "Save a new doctor"
    )
    public ResponseEntity<DoctorDto> saveDoctor(@PathVariable("department-id") @ValidDepartmentId Long departmentId,
                                                @RequestBody @Valid ReqDoctorDto reqDoctor) {

        Doctor doctor = doctorMapper.toEntityForCreate(departmentId, reqDoctor);
        Doctor savedDoctor = doctorService.save(doctor);
        DoctorDto result = doctorMapper.toDto(savedDoctor);

        return ResponseEntity
                .created(URI.create(String.format("doctors/%s", result.getId())))
                .body(result);
    }

    @PatchMapping("/{doctor-id}")
    @Operation(
            method = "PATCH",
            summary = "Update a doctor"
    )
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable("doctor-id") @ValidDoctor Long doctorId,
                                                  @RequestBody @Valid ReqDoctorDtoPatch reqDoctor) {

        Doctor doctor = doctorService.getById(doctorId);
        Doctor updatedDoctor = doctorMapper.update(reqDoctor, doctor);
        Doctor savedDoctor = doctorService.save(updatedDoctor);
        DoctorDto result = doctorMapper.toDto(savedDoctor);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{doctor-id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a doctor"
    )
    public ResponseEntity<?> deleteDoctor(@PathVariable("doctor-id") @ValidDoctor Long doctorId) {

        doctorService.deleteById(doctorId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
