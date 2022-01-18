package com.example.patients.controller;

import com.example.patients.constraint.annotation.ValidMedication;
import com.example.patients.dto.MedicationDto;
import com.example.patients.dto.input.ReqMedicationDto;
import com.example.patients.mapper.MedicationMapper;
import com.example.patients.model.Medication;
import com.example.patients.service.MedicationService;
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
@RequestMapping(value = "/medications")
@Validated
@Api(value = "/medications",
        tags = "Medications")
public class MedicationController {

    private final MedicationService medicationService;
    private final MedicationMapper medicationMapper;

    @Autowired
    public MedicationController(MedicationService medicationService, MedicationMapper medicationMapper) {
        this.medicationService = medicationService;
        this.medicationMapper = medicationMapper;
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all medications"
    )
    public ResponseEntity<List<MedicationDto>> getAll() {

        List<MedicationDto> result = medicationService.getAllMedications()
                .stream().map(medicationMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{medication-id}")
    @Operation(
            method = "GET",
            summary = "Get a medication by ID"
    )
    public ResponseEntity<MedicationDto> getMedicationById(@PathVariable("medication-id") Long medicationId) {

        MedicationDto result = medicationMapper.toDto(medicationService.getMedicationById(medicationId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Save a new medication"
    )
    public ResponseEntity<MedicationDto> saveMedication(@RequestBody @Valid ReqMedicationDto reqMedication) {

        Medication medication = medicationMapper.toEntity(reqMedication);
        Medication savedMedication = medicationService.saveMedication(medication);
        MedicationDto result = medicationMapper.toDto(savedMedication);

        return ResponseEntity
                .created(URI.create(String.format("medications/%s", result.getId())))
                .body(result);
    }

    @PutMapping("/{medication-id}")
    @Operation(
            method = "PUT",
            summary = "Update a medication"
    )
    public ResponseEntity<MedicationDto> updateMedication(@PathVariable("medication-id") Long medicationId,
                                                          @RequestBody @Valid ReqMedicationDto reqMedication) {

        Medication medication = medicationService.getMedicationById(medicationId);
        Medication updatedMedication = medicationService.updateMedication(reqMedication, medication);
        MedicationDto result = medicationMapper.toDto(updatedMedication);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{medication-id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a medication"
    )
    public ResponseEntity<Void> deleteDoctor(@PathVariable("medication-id") @ValidMedication Long medicationId) {

        medicationService.deleteMedicationById(medicationId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
