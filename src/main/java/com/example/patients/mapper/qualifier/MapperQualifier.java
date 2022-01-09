package com.example.patients.mapper.qualifier;

import com.example.patients.model.Address;
import com.example.patients.model.Department;
import com.example.patients.model.Medication;
import com.example.patients.service.AddressService;
import com.example.patients.service.DepartmentService;
import com.example.patients.service.MedicationService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperQualifier {

    private final AddressService addressService;
    private final DepartmentService departmentService;
    private final MedicationService medicationService;

    public MapperQualifier(AddressService addressService, DepartmentService departmentService, MedicationService medicationService) {
        this.addressService = addressService;
        this.departmentService = departmentService;
        this.medicationService = medicationService;
    }

    @Named("idToDepartment")
    public Department idToDepartment(Long id) {
        return departmentService.getDepartmentById(id);
    }

    @Named("idToAddress")
    public Address idToAddress(Long id) {
        return addressService.getAddressById(id);
    }

    @Named("idsToMedications")
    public List<Medication> idToMedications(List<Long> ids) {
        return ids.stream()
                .map(medicationService::getMedicationById)
                .collect(Collectors.toList());
    }
}
