package com.example.patients.controller;

import com.example.patients.dto.ConsultDto;
import com.example.patients.dto.input.ReqConsultDto;
import com.example.patients.mapper.ConsultMapper;
import com.example.patients.model.Consult;
import com.example.patients.service.ConsultService;
import com.example.patients.service.constraint.ValidConsult;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/consults")
@Validated
@Api(value = "/consults",
        tags = "Consults")
public class ConsultController {

    private final ConsultService consultService;
    private final ConsultMapper consultMapper;

    @Autowired
    public ConsultController(ConsultService consultService, ConsultMapper consultMapper) {
        this.consultService = consultService;
        this.consultMapper = consultMapper;
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all consults"
    )
    public ResponseEntity<List<ConsultDto>> getAll() {

        List<ConsultDto> result = consultService.getAll().stream()
                .map(consultMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/filtered")
    @Operation(
            method = "GET",
            summary = "Get all consults between patient and doctor"
    )
    public ResponseEntity<List<ConsultDto>> getAllConsultsForDoctorAndPatient(@RequestParam("doctorId") @ValidDoctor Long doctorId,
                                                                              @RequestParam("patientId") @ValidPatient Long patientId) {

        List<ConsultDto> result = new ArrayList<>();

        List<Consult> consults = consultService.getAllConsultsForDoctorAndPatient(doctorId, patientId);

        if (consults != null) {
            result = consults.stream()
                    .map(consultMapper::toDto)
                    .collect(Collectors.toList());
        }

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{consult-id}")
    @Operation(
            method = "GET",
            summary = "Get a consult by ID"
    )
    public ResponseEntity<ConsultDto> getById(@PathVariable("consult-id") @ValidConsult Long consultId) {

        ConsultDto result = consultMapper.toDto(consultService.getById(consultId));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Save a new consult"
    )
    public ResponseEntity<ConsultDto> saveConsult(@RequestBody @Valid ReqConsultDto reqConsult) {

        Consult consult = consultMapper.toEntity(reqConsult);
        Consult savedConsult = consultService.save(consult);
        ConsultDto result = consultMapper.toDto(savedConsult);

        return ResponseEntity
                .created(URI.create(String.format("consults/%s", result.getId())))
                .body(result);

    }

    @PatchMapping("/{consult-id}")
    @Operation(
            method = "PATCH",
            summary = "Update a consult"
    )
    public ResponseEntity<ConsultDto> updateConsult(@PathVariable("consult-id") @ValidConsult Long consultId,
                                                    @RequestBody @Valid ReqConsultDto reqConsult) {

        Consult consult = consultService.getById(consultId);
        Consult updatedConsult = consultMapper.update(reqConsult, consult);
        Consult savedConsult = consultService.save(updatedConsult);
        ConsultDto result = consultMapper.toDto(savedConsult);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{consult-id}")
    @Operation(
            method = "DELETE",
            summary = "Delete a consult"
    )
    public ResponseEntity<?> deleteDoctor(@PathVariable("consult-id") @ValidConsult Long consultId) {

        consultService.deleteById(consultId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
