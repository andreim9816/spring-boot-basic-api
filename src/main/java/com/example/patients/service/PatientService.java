package com.example.patients.service;

import com.example.patients.dto.input.update.ReqPatientUpdateDto;
import com.example.patients.exception.CustomException;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.PatientMapper;
import com.example.patients.model.Consult;
import com.example.patients.model.Medication;
import com.example.patients.model.Patient;
import com.example.patients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final AddressService addressService;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientService(AddressService addressService, PatientRepository patientRepository, PatientMapper patientMapper) {
        this.addressService = addressService;
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(patientId)
                        .entityType("Patient")
                        .build()
                );
    }

    public Boolean checkIfPatientExists(Long patientId) {
        return patientRepository.findById(patientId).isPresent();
    }

    public Boolean checkIfCnpExists(String cnp) {
        return patientRepository.getByCnp(cnp) != null;
    }

    public List<Consult> getConsultsForPatient(Long patientId) {
        Patient patient = getPatientById(patientId);
        return getConsultsForPatient(patient);
    }

    private List<Consult> getConsultsForPatient(Patient patient) {
        return patient.getConsults();
    }

    public Patient getLongestHospitalizedPatient() {
        List<Patient> patients = getAllPatients();

        if (patients != null) {
            return getAllPatients().stream()
                    .map(this::getConsultsForPatient)
                    .flatMap(Collection::stream)
                    .min(Comparator.comparing(Consult::getDate))
                    .map(Consult::getPatient)
                    .orElse(null);
        }

        return null;
    }

    public List<Medication> getUniqueMedicationsForPatient(Long patientId) {
        return getConsultsForPatient(patientId).stream()
                .map(Consult::getMedications)
                .flatMap(Collection::stream)
                .distinct()
                .sorted(Comparator.comparing(Medication::getId))
                .collect(Collectors.toList());
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(ReqPatientUpdateDto reqPatientDto, Patient patient) {

        /* Check for uniqueness address at DB level */
        if (!Objects.equals(reqPatientDto.getAddressId(), patient.getAddress().getId())
                && Boolean.TRUE.equals(addressService.checkIfAddressIsTakenByPatient(reqPatientDto.getAddressId()))) {
            throw new CustomException(String.format("Address with id %s already taken!", reqPatientDto.getAddressId()));
        }

        /* Check for uniqueness of CNP */
        if (!Objects.equals(reqPatientDto.getCnp(), patient.getCnp())
                && Boolean.TRUE.equals(checkIfCnpExists(reqPatientDto.getCnp()))) {
            throw new CustomException(String.format("CNP %s already taken!", reqPatientDto.getCnp()));
        }

        Patient updatedPatient = patientMapper.update(reqPatientDto, patient);

        return savePatient(updatedPatient);
    }

    public void deletePatientById(Long patientId) {
        patientRepository.deleteById(patientId);
    }
}
