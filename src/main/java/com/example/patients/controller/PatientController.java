package com.example.patients.controller;

import com.example.patients.dto.PatientDto;
import com.example.patients.dto.input.ReqPatientDto;
import com.example.patients.dto.input.patch.ReqPatientDtoPatch;
import com.example.patients.mapper.PatientMapper;
import com.example.patients.model.Patient;
import com.example.patients.service.PatientService;
import com.example.patients.service.constraint.ValidDepartmentId;
import com.example.patients.service.constraint.ValidPatient;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/patients")
@Validated
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public PatientController(PatientService patientService, PatientMapper patientMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all patients"
    )
    public ResponseEntity<List<PatientDto>> getAll() {

        List<PatientDto> result = patientService.getAll()
                .stream().map(patientMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{patient-id}")
    @Operation(
            method = "GET",
            summary = "Get a patient by ID"
    )
    public ResponseEntity<PatientDto> getById(@PathVariable("patient-id") @ValidPatient Long id) {

        PatientDto result = patientMapper.toDto(patientService.getById(id));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/{department-id}")
    @Operation(
            method = "POST",
            summary = "Save a new patient"
    )
    public ResponseEntity<PatientDto> savePatient(@PathVariable("department-id") @ValidDepartmentId Long departmentId,
                                                 @RequestBody @Valid ReqPatientDto reqPatient) {

        Patient patient = patientMapper.toEntityForCreate(departmentId, reqPatient);
        Patient savedPatient = patientService.save(patient);
        PatientDto result = patientMapper.toDto(savedPatient);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping("/{patient-id}")
    @Operation(
            method = "PATCH",
            summary = "Update a patient"
    )
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("patient-id") @ValidPatient Long patientId,
                                                   @RequestBody @Valid ReqPatientDtoPatch reqPatient) {

        Patient patient = patientService.getById(patientId);
        Patient updatedPatient = patientMapper.update(reqPatient, patient);
        Patient savedPatient = patientService.save(updatedPatient);
        PatientDto result = patientMapper.toDto(savedPatient);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{patient-id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a patient"
    )
    public ResponseEntity<?> deletePatient(@PathVariable("patient-id") @ValidPatient Long patientId) {

        patientService.deleteById(patientId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
