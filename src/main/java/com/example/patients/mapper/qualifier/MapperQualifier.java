package com.example.patients.mapper.qualifier;

import com.example.patients.model.*;
import com.example.patients.service.*;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperQualifier {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AddressService addressService;
    private final DepartmentService departmentService;
    private final MedicationService medicationService;

    @Autowired
    public MapperQualifier(DoctorService doctorService, PatientService patientService, AddressService addressService,
                           DepartmentService departmentService, MedicationService medicationService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.addressService = addressService;
        this.departmentService = departmentService;
        this.medicationService = medicationService;
    }

    @Named("idToDepartment")
    public Department idToDepartment(Long id) {
        return departmentService.getById(id);
    }

    @Named("idToAddress")
    public Address idToAddress(Long id) {
        return addressService.getById(id);
    }

    @Named("idToDoctor")
    public Doctor idToDoctor(Long id) {
        return doctorService.getById(id);
    }

    @Named("idToPatient")
    public Patient idToPatient(Long id) {
        return patientService.getById(id);
    }

    @Named("idsToMedications")
    public List<Medication> idToMedications(List<Long> ids) {
        return ids.stream()
                .map(medicationService::getById)
                .collect(Collectors.toList());
    }
}
