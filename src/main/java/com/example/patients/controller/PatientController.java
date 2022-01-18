package com.example.patients.controller;

import com.example.patients.constraint.annotation.ValidPatient;
import com.example.patients.dto.ConsultDto;
import com.example.patients.dto.MedicationDto;
import com.example.patients.dto.PatientDto;
import com.example.patients.dto.input.ReqPatientDto;
import com.example.patients.dto.input.update.ReqPatientUpdateDto;
import com.example.patients.mapper.ConsultMapper;
import com.example.patients.mapper.MedicationMapper;
import com.example.patients.mapper.PatientMapper;
import com.example.patients.model.Patient;
import com.example.patients.service.AddressService;
import com.example.patients.service.PatientService;
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
@RequestMapping(value = "/patients")
@Validated
@Api(value = "/patients",
        tags = "Patients")
public class PatientController {

    private final PatientService patientService;
    private final AddressService addressService;
    private final PatientMapper patientMapper;
    private final ConsultMapper consultMapper;
    private final MedicationMapper medicationMapper;

    @Autowired
    public PatientController(PatientService patientService, AddressService addressService, PatientMapper patientMapper, ConsultMapper consultMapper, MedicationMapper medicationMapper) {
        this.patientService = patientService;
        this.addressService = addressService;
        this.patientMapper = patientMapper;
        this.consultMapper = consultMapper;
        this.medicationMapper = medicationMapper;
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all patients"
    )
    public ResponseEntity<List<PatientDto>> getAll() {

        List<PatientDto> result = patientService.getAllPatients()
                .stream().map(patientMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    //TODO maybe create another DTO???
    @GetMapping("/{patient-id}/consults")
    @Operation(
            method = "GET",
            summary = "Get all consultations for a patient"
    )
    public ResponseEntity<List<ConsultDto>> getAllConsultationForPatient(@PathVariable("patient-id") Long patientId) {

        List<ConsultDto> result = patientService.getPatientById(patientId)
                .getConsults()
                .stream()
                .map(consultMapper::toDto)
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
    public ResponseEntity<PatientDto> getPatientById(@PathVariable("patient-id") Long patientId) {

        PatientDto result = patientMapper.toDto(patientService.getPatientById(patientId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/longestHospitalized")
    @Operation(
            method = "GET",
            summary = "Get the longest hospitalized patient"
    )
    public ResponseEntity<?> getLongestHospitalizedPatient() {

        Patient patient = patientService.getLongestHospitalizedPatient();
        if (patient != null) {
            PatientDto result = patientMapper.toDto(patient);

            return ResponseEntity
                    .ok()
                    .body(result);
        }
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/{patient-id}/medications")
    @Operation(
            method = "GET",
            summary = "Get all medications administrated to a patient"
    )
    public ResponseEntity<List<MedicationDto>> getMedicationsForPatient(@PathVariable("patient-id") Long patientId) {

        List<MedicationDto> medications = patientService.getUniqueMedicationsForPatient(patientId).stream()
                .map(medicationMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(medications);
    }

    @PostMapping("/{department-id}")
    @Operation(
            method = "POST",
            summary = "Save a new patient"
    )
    public ResponseEntity<PatientDto> savePatient(@PathVariable("department-id") Long departmentId,
                                                  @RequestBody @Valid ReqPatientDto reqPatient) {

        Patient patient = patientMapper.toEntityForCreate(departmentId, reqPatient);
        Patient savedPatient = patientService.savePatient(patient);
        PatientDto result = patientMapper.toDto(savedPatient);

        return ResponseEntity
                .created(URI.create(String.format("patients/%s", result.getId())))
                .body(result);
    }

    @PutMapping("/{patient-id}")
    @Operation(
            method = "PUT",
            summary = "Update a patient"
    )
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("patient-id") Long patientId,
                                                    @RequestBody @Valid ReqPatientUpdateDto reqPatient) {

        Patient patient = patientService.getPatientById(patientId);
        Patient updatedPatient = patientService.updatePatient(reqPatient, patient);
        PatientDto result = patientMapper.toDto(updatedPatient);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{patient-id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a patient"
    )
    public ResponseEntity<Void> deletePatient(@PathVariable("patient-id") @ValidPatient Long patientId) {

        patientService.deletePatientById(patientId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
