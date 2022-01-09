package com.example.patients.service;

import com.example.patients.dto.input.ReqDepartmentDto;
import com.example.patients.exception.CustomException;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.DepartmentMapper;
import com.example.patients.model.Department;
import com.example.patients.model.Doctor;
import com.example.patients.model.Patient;
import com.example.patients.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
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

    public Optional<Department> getDepartmentByName(String departmentName) {
        return Optional.ofNullable(departmentRepository.findDepartmentByName(departmentName));
    }

    public Department updateDepartment(ReqDepartmentDto reqDepartmentDto, Department department) {
        Department updatedDepartment = departmentMapper.update(reqDepartmentDto, department);
        return saveDepartment(updatedDepartment);
    }

    public Department saveDepartment(Department department) {

        Optional<Department> departmentByName = getDepartmentByName(department.getName());
        if (departmentByName.isPresent()) {
            throw new CustomException(String.format("Department with name %s already exists!", department.getName()));
        }

        return departmentRepository.save(department);
    }

    public void deleteDepartmentById(Long id) {
        departmentRepository.deleteById(id);
    }
}
