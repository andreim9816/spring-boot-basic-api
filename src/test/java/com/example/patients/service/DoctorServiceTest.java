package com.example.patients.service;

import com.example.patients.dto.input.patch.ReqDoctorDtoPatch;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.DoctorMapper;
import com.example.patients.model.Doctor;
import com.example.patients.repository.DoctorRepository;
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
class DoctorServiceTest {

    @Mock
    DoctorRepository doctorRepository;

    @Mock
    DoctorMapper doctorMapper;

    @InjectMocks
    DoctorService doctorService;

    @Test
    @DisplayName("Get all doctors")
    void getAllDoctors() {
        Doctor doctor1 = Doctor.builder()
                .id(1L)
                .firstName("First name 1")
                .lastName("Last name 1")
                .build();

        Doctor doctor2 = Doctor.builder()
                .id(2L)
                .firstName("First name 2")
                .lastName("Last name 2")
                .build();

        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor1, doctor2));

        List<Doctor> result = doctorService.getAllDoctors();

        assertEquals(result.get(0).getId(), doctor1.getId());
        assertEquals(result.get(0).getFirstName(), doctor1.getFirstName());
        assertEquals(result.get(0).getLastName(), doctor1.getLastName());

        assertEquals(result.get(1).getId(), doctor2.getId());
        assertEquals(result.get(1).getFirstName(), doctor2.getFirstName());
        assertEquals(result.get(1).getLastName(), doctor2.getLastName());
    }

    @Test
    @DisplayName("Get doctor by ID successful")
    void getDoctorById() {
        Long doctorId = 1L;
        Doctor doctor = Doctor.builder()
                .id(doctorId)
                .firstName("First name")
                .lastName("Last name")
                .build();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.getById(doctorId);

        assertEquals(doctor.getId(), result.getId());
        assertEquals(doctor.getFirstName(), result.getFirstName());
        assertEquals(doctor.getLastName(), result.getLastName());
    }

    @Test
    @DisplayName("Check if doctors exists")
    void checkIfDoctorExists() {
        Long doctorId = 1L;

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(Doctor.builder().build()));

        Boolean result = doctorService.checkIfDoctorExists(doctorId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Get doctor by ID FAILED")
    void getDoctorByIdFailed() {
        Long doctorId = 1L;

        EntityNotFoundException exceptionToBeThrown = EntityNotFoundException.builder()
                .entityId(doctorId)
                .entityType("Doctor")
                .build();

        when(doctorRepository.findById(doctorId)).thenThrow(exceptionToBeThrown);

        EntityNotFoundException exception = null;

        try {
            doctorService.getById(doctorId);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(exceptionToBeThrown.getEntityId(), exception.getEntityId());
        assertEquals(exceptionToBeThrown.getEntityType(), exception.getEntityType());
    }

    @Test
    @DisplayName("Save doctor")
    void saveDoctor() {
        Long doctorId = 1L;
        String firstName = "First name";
        String lastName = "Last name";

        Doctor doctorToBeSaved = Doctor.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Doctor doctorSaved = Doctor.builder()
                .id(doctorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        when(doctorRepository.save(doctorToBeSaved)).thenReturn(doctorSaved);

        Doctor result = doctorService.saveDoctor(doctorToBeSaved);

        assertEquals(doctorSaved.getId(), result.getId());
        assertEquals(doctorSaved.getFirstName(), result.getFirstName());
        assertEquals(doctorSaved.getLastName(), result.getLastName());
    }

    @Test
    @DisplayName("Update doctor")
    void updateDepartment() {
        Long doctorId = 1L;
        String firstName = "First name";
        String lastName = "Last name";
        String firstNameUpdated = "First name updated";
        String lastNameUpdated = "Last name updated";

        ReqDoctorDtoPatch reqDoctorDtoPatch = new ReqDoctorDtoPatch();
        reqDoctorDtoPatch.setFirstName(firstNameUpdated);
        reqDoctorDtoPatch.setLastName(lastNameUpdated);

        Doctor doctorToBeUpdated = Doctor.builder()
                .id(doctorId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Doctor doctorUpdated = Doctor.builder()
                .id(doctorId)
                .firstName(firstNameUpdated)
                .lastName(lastNameUpdated)
                .build();

        Doctor doctorSaved = Doctor.builder()
                .id(doctorId)
                .firstName(firstNameUpdated)
                .lastName(lastNameUpdated)
                .build();


        when(doctorMapper.update(reqDoctorDtoPatch, doctorToBeUpdated)).thenReturn(doctorUpdated);

        when(doctorRepository.save(any())).thenReturn(doctorSaved);

        Doctor result = doctorService.updateDoctor(reqDoctorDtoPatch, doctorToBeUpdated);

        assertEquals(doctorSaved.getId(), result.getId());
        assertEquals(doctorSaved.getFirstName(), result.getFirstName());
        assertEquals(doctorSaved.getLastName(), result.getLastName());
    }

    @Test
    @DisplayName("Delete doctor")
    void deleteDoctor() {
        Long doctorId = 1L;

        doNothing().when(doctorRepository).deleteById(doctorId);

        doctorService.deleteDoctorById(doctorId);

        verify(doctorRepository, times(1)).deleteById(doctorId);
    }
}
