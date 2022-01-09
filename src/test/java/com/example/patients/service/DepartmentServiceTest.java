package com.example.patients.service;

import com.example.patients.dto.input.ReqDepartmentDto;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.model.Department;
import com.example.patients.model.Doctor;
import com.example.patients.model.Patient;
import com.example.patients.repository.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentService departmentService;

    @Test
    @DisplayName("Get all departments")
    void getAllDepartments() {
        Department department1 = Department.builder()
                .id(1L)
                .name("Department 1")
                .build();

        Department department2 = Department.builder()
                .id(2L)
                .name("Department 2")
                .build();

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));

        List<Department> result = departmentService.getAllDepartments();

        assertEquals(result.get(0), department1);
        assertEquals(result.get(1), department2);
    }

    @Test
    @DisplayName("Get department by ID successful")
    void getDepartmentById() {
        Long departmentId = 1L;
        Department department = Department.builder()
                .id(departmentId)
                .name("Department 1")
                .build();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(departmentId);

        assertEquals(department, result);
    }

    @Test
    @DisplayName("Get department by ID FAILED")
    void getDepartmentByIdFailed() {
        Long departmentId = 1L;

        EntityNotFoundException exceptionToBeThrown = EntityNotFoundException.builder()
                .entityId(departmentId)
                .entityType("Department")
                .build();

        when(departmentRepository.findById(departmentId)).thenThrow(exceptionToBeThrown);

        EntityNotFoundException exception = null;

        try {
            departmentService.getDepartmentById(departmentId);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(exceptionToBeThrown.getEntityId(), exception.getEntityId());
        assertEquals(exceptionToBeThrown.getEntityType(), exception.getEntityType());
    }

    @Test
    @DisplayName("Get all patients in a department")
    void getAllPatientsInDepartment() {
        Long departmentId = 1L;
        String departmentName = "Dep name";

        Long patient1Id = 10L, patient2Id = 11L;
        String firstName1 = "First name 1", lastName1 = "Last name 1";
        String firstName2 = "First name 2", lastName2 = "Last name 2";

        Patient patient1 = Patient.builder()
                .id(patient1Id)
                .firstName(firstName1)
                .lastName(lastName1)
                .build();

        Patient patient2 = Patient.builder()
                .id(patient2Id)
                .firstName(firstName2)
                .lastName(lastName2)
                .build();

        Department department = Department.builder()
                .id(departmentId)
                .name(departmentName)
                .patients(Arrays.asList(patient1, patient2))
                .build();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        List<Patient> result = departmentService.getAllPatientsInDepartment(departmentId);

        assertEquals(2, result.size());
        assertEquals(result.get(0), patient1);
        assertEquals(result.get(1), patient2);
    }

    @Test
    @DisplayName("Get all doctors in a department")
    void getAllDoctorsInDepartment() {
        Long departmentId = 1L;
        String departmentName = "Dep name";

        Long doctor1Id = 10L, doctor2Id = 11L;
        String firstName1 = "First name 1", lastName1 = "Last name 1";
        String firstName2 = "First name 2", lastName2 = "Last name 2";

        Doctor doctor1 = Doctor.builder()
                .id(doctor1Id)
                .firstName(firstName1)
                .lastName(lastName1)
                .build();

        Doctor doctor2 = Doctor.builder()
                .id(doctor2Id)
                .firstName(firstName2)
                .lastName(lastName2)
                .build();

        Department department = Department.builder()
                .id(departmentId)
                .name(departmentName)
                .doctors(Arrays.asList(doctor1, doctor2))
                .build();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        List<Doctor> result = departmentService.getAllDoctorsInDepartment(departmentId);

        assertEquals(2, result.size());
        assertEquals(result.get(0), doctor1);
        assertEquals(result.get(1), doctor2);
    }

    @Test
    @DisplayName("Get department by name successful")
    void getDepartmentByName() {
        Long departmentId = 1L;
        String departmentName = "Department name";

        Department department = Department.builder()
                .id(departmentId)
                .name(departmentName)
                .build();

        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(department);

        Optional<Department> result = departmentService.getDepartmentByName(departmentName);

        assertTrue(result.isPresent());
        assertEquals(department, result.get());
    }

    @Test
    @DisplayName("Get department by name FAILED")
    void getDepartmentByNameFailed() {
        String departmentName = "Department name";

        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(null);

        Optional<Department> nullDepartment = departmentService.getDepartmentByName(departmentName);

        assertTrue(nullDepartment.isEmpty());
    }

    @Test
    @DisplayName("Check if department exists")
    void checkIfDepartmentExists() {
        Long departmentId = 1L;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));

        Boolean result = departmentService.checkIfDepartmentExists(departmentId);

        assertTrue(result);
    }

    @Test
    @DisplayName("Check if department exists FAILED")
    void checkIfDepartmentExistsFailed() {
        Long departmentId = 1L;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        Boolean result = departmentService.checkIfDepartmentExists(departmentId);

        assertFalse(result);
    }

//    @Test
//    @DisplayName("Update department")
//    void updateDepartment() {
//        Long departmentId = 1L;
//        String departmentName = "Department name";
//        String departmentNameUpdated = "New department name";
//
//        ReqDepartmentDto reqDepartmentDto = new ReqDepartmentDto();
//        reqDepartmentDto.setName(departmentNameUpdated);
//
//        Department departmentToBeUpdated = Department.builder()
//                .id(departmentId)
//                .name(departmentName)
//                .build();
//
//        Department departmentSaved = Department.builder()
//                .id(departmentId)
//                .name(departmentNameUpdated)
//                .build();
//
//
//        when(departmentRepository.save(any())).thenReturn(departmentSaved);
//
//        Department result = departmentService.updateDepartment(reqDepartmentDto, departmentToBeUpdated);
//
//        assertEquals(departmentSaved, result);
//    }

    @Test
    @DisplayName("Save department")
    void saveDepartment() {
        Long departmentId = 1L;
        String departmentName = "Department name";

        Department departmentToBeSaved = Department.builder()
                .name(departmentName)
                .build();

        Department departmentSaved = Department.builder()
                .id(departmentId)
                .name(departmentName)
                .build();

        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(null);
        when(departmentRepository.save(departmentToBeSaved)).thenReturn(departmentSaved);

        Department result = departmentService.saveDepartment(departmentToBeSaved);

        assertEquals(departmentSaved.getId(), result.getId());
        assertEquals(departmentSaved.getName(), result.getName());
    }

//    @Test
//    @DisplayName("Save department FAILED")
//    void saveDepartmentFailed() {
//        Long departmentId = 1L;
//        String departmentName = "Department name";
//
//        Department department = Department.builder()
//                .id(departmentId)
//                .name(departmentName)
//                .build();
//
//        Department existingDepartment = Department.builder()
//                .id(2L)
//                .name("Already existing dep name")
//                .build();
//
//
//        when(departmentRepository.findDepartmentByName(departmentName)).thenReturn(existingDepartment);
//
//        CustomException exception = assertThrows(CustomException.class, () -> departmentService.saveDepartment(department));
//
//        assertEquals(String.format("Department with name %s already exists!", department.getName()), exception.getMessage());
//    }

    @Test
    @DisplayName("Delete department")
    void deleteDepartment() {
        Long departmentId = 1L;

        doNothing().when(departmentRepository).deleteById(departmentId);

        departmentService.deleteDepartmentById(departmentId);

        verify(departmentRepository, times(1)).deleteById(departmentId);
    }
}
