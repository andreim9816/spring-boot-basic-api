package com.example.patients.service;

import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.model.Department;
import com.example.patients.model.Doctor;
import com.example.patients.model.Patient;
import com.example.patients.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Doctor> getAllDoctorsInDepartment(Long departmentId) {
        return getDepartmentById(departmentId).getDoctors();
    }

    public List<Patient> getAllPatientsInDepartment(Long departmentId) {
        return getDepartmentById(departmentId).getPatients();
    }

    public Department getDepartmentById(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() -> EntityNotFoundException.builder()
                        .entityId(id)
                        .entityType("Department")
                        .build()
                );
    }

    public Boolean checkIfDepartmentExists(Long id) {
        return departmentRepository.findById(id).isPresent();
    }

    public Department getDepartmentByName(String departmentName) {
        Department department = departmentRepository.findDepartmentByName(departmentName);

        if (department == null) {
            throw new javax.persistence.EntityNotFoundException(String.format("Department with name %s does not exist!", departmentName));
        }

        return department;
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }
}
