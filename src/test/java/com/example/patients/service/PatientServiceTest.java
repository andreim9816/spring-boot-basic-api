package com.example.patients.service;

import com.example.patients.dto.input.patch.ReqPatientDtoPatch;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.PatientMapper;
import com.example.patients.model.Consult;
import com.example.patients.model.Medication;
import com.example.patients.model.Patient;
import com.example.patients.repository.PatientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @Mock
    PatientMapper patientMapper;

    @InjectMocks
    PatientService patientService;

    @Test
    @DisplayName("Get all patients")
    void getAllPatients() {
        Patient patient1 = Patient.builder()
                .id(1L)
                .firstName("First name 1")
                .lastName("Last name 1")
                .build();

        Patient patient2 = Patient.builder()
                .id(2L)
                .firstName("First name 2")
                .lastName("Last name 2")
                .build();

        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));

        List<Patient> result = patientService.getAllPatients();

        assertEquals(result.get(0).getId(), patient1.getId());
        assertEquals(result.get(0).getFirstName(), patient1.getFirstName());
        assertEquals(result.get(0).getLastName(), patient1.getLastName());
        assertEquals(result.get(1).getId(), patient2.getId());
        assertEquals(result.get(1).getFirstName(), patient2.getFirstName());
        assertEquals(result.get(1).getLastName(), patient2.getLastName());
    }

    @Test
    @DisplayName("Get patient by ID successful")
    void getPatientById() {
        Long patientId = 1L;
        Patient patient = Patient.builder()
                .id(patientId)
                .firstName("First name")
                .lastName("Last name")
                .build();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        Patient result = patientService.getPatientById(patientId);

        assertEquals(result.getId(), patient.getId());
        assertEquals(result.getFirstName(), patient.getFirstName());
        assertEquals(result.getLastName(), patient.getLastName());
    }


    @Test
    @DisplayName("Get patient by ID FAILED")
    void getPatientByIdFailed() {
        Long patientId = 1L;

        EntityNotFoundException exceptionToBeThrown = EntityNotFoundException.builder()
                .entityId(patientId)
                .entityType("Patient")
                .build();

        when(patientRepository.findById(patientId)).thenThrow(exceptionToBeThrown);

        EntityNotFoundException exception = null;

        try {
            patientService.getPatientById(patientId);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(exceptionToBeThrown.getEntityId(), exception.getEntityId());
        assertEquals(exceptionToBeThrown.getEntityType(), exception.getEntityType());
    }

    @Test
    @DisplayName("Check if patients exists")
    void checkIfPatientExists() {
        Long patientId = 1L;

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(Patient.builder().build()));

        Boolean result = patientService.checkIfPatientExists(patientId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if cnp exists")
    void checkIfCnpExists() {
        String cnp = "1234567890123";
        when(patientRepository.getByCnp(cnp)).thenReturn(Patient.builder().build());

        Boolean result = patientService.checkIfCnpExists(cnp);
        assertTrue(result);
    }

    @Test
    @DisplayName("Get consults for patient")
    void getConsultsForPatient() {
        Long patientId = 1L;

        Consult consult1 = Consult.builder()
                .id(1L)
                .comment("comment 1")
                .diagnose("diagnose 1")
                .build();

        Consult consult2 = Consult.builder()
                .id(2L)
                .comment("comment 2")
                .diagnose("diagnose 2")
                .build();

        Consult consult3 = Consult.builder()
                .id(3L)
                .comment("comment 3")
                .diagnose("diagnose 3")
                .build();

        Patient patient = Patient.builder()
                .id(1L)
                .firstName("First name 1")
                .lastName("Last name 1")
                .consults(Arrays.asList(consult1, consult2, consult3))
                .build();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        List<Consult> result = patientService.getConsultsForPatient(patientId);

        assertEquals(consult1.getId(), result.get(0).getId());
        assertEquals(consult1.getComment(), result.get(0).getComment());
        assertEquals(consult1.getDiagnose(), result.get(0).getDiagnose());

        assertEquals(consult2.getId(), result.get(1).getId());
        assertEquals(consult2.getComment(), result.get(1).getComment());
        assertEquals(consult2.getDiagnose(), result.get(1).getDiagnose());

        assertEquals(consult3.getId(), result.get(2).getId());
        assertEquals(consult3.getComment(), result.get(2).getComment());
        assertEquals(consult3.getDiagnose(), result.get(2).getDiagnose());
    }

    @Test
    @DisplayName("Get unique medications for patient")
    void getUniqueMedicationsInConsults() {

        Medication medication1 = Medication.builder()
                .id(1L)
                .name("Medication 1")
                .quantity(20)
                .build();

        Medication medication2 = Medication.builder()
                .id(2L)
                .name("Medication 2")
                .quantity(50)
                .build();

        Medication medication3 = Medication.builder()
                .id(3L)
                .name("Medication 3")
                .quantity(10)
                .build();

        Medication medication4 = Medication.builder()
                .id(4L)
                .name("Medication 4")
                .quantity(100)
                .build();

        Consult consult1 = Consult.builder()
                .id(1L)
                .medications(Arrays.asList(medication1, medication3))
                .build();

        Consult consult2 = Consult.builder()
                .id(2L)
                .medications(Arrays.asList(medication3, medication4))
                .build();

        Consult consult3 = Consult.builder()
                .id(3L)
                .medications(Arrays.asList(medication1, medication2, medication4))
                .build();

        Consult consult4 = Consult.builder()
                .id(4L)
                .medications(Arrays.asList(medication3, medication4))
                .build();


        Patient patient = Patient.builder()
                .id(1L)
                .consults(Arrays.asList(consult1, consult2, consult3, consult4))
                .build();

        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        List<Medication> result = patientService.getUniqueMedicationsForPatient(patient.getId());

        assertEquals(result.get(0).getId(), medication1.getId());
        assertEquals(result.get(0).getQuantity(), medication1.getQuantity());
        assertEquals(result.get(0).getName(), medication1.getName());

        assertEquals(result.get(1).getId(), medication2.getId());
        assertEquals(result.get(1).getQuantity(), medication2.getQuantity());
        assertEquals(result.get(1).getName(), medication2.getName());

        assertEquals(result.get(2).getId(), medication3.getId());
        assertEquals(result.get(2).getQuantity(), medication3.getQuantity());
        assertEquals(result.get(2).getName(), medication3.getName());

        assertEquals(result.get(3).getId(), medication4.getId());
        assertEquals(result.get(3).getQuantity(), medication4.getQuantity());
        assertEquals(result.get(3).getName(), medication4.getName());
    }

    @Test
    @DisplayName("Get longest hospitalized patient")
    void getLongestHospitalizedPatient() {

        Consult consult1 = Consult.builder().id(1L).date(new Date(System.currentTimeMillis() - 1000))
                .build();

        Consult consult2 = Consult.builder().id(2L).date(new Date(System.currentTimeMillis() - 500))
                .build();

        Consult consult3 = Consult.builder().id(3L).date(new Date(System.currentTimeMillis() - 300))
                .build();

        Consult consult4 = Consult.builder().id(4L).date(new Date(System.currentTimeMillis() - 200))
                .build();

        Consult consult5 = Consult.builder().id(5L).date(new Date(System.currentTimeMillis() - 200))
                .build();

        Consult consult6 = Consult.builder().id(6L).date(new Date(System.currentTimeMillis() - 100))
                .build();

        Consult consult7 = Consult.builder().id(7L).date(new Date(System.currentTimeMillis() - 70))
                .build();

        Consult consult8 = Consult.builder().id(8L).date(new Date(System.currentTimeMillis() - 30))
                .build();

        Patient patient1 = Patient.builder()
                .id(1L)
                .consults(Arrays.asList(consult1, consult2, consult3))
                .build();

        consult1.setPatient(patient1);
        consult2.setPatient(patient1);
        consult3.setPatient(patient1);

        Patient patient2 = Patient.builder()
                .id(1L)
                .consults(Arrays.asList(consult4, consult5))
                .build();

        consult4.setPatient(patient2);
        consult5.setPatient(patient2);

        Patient patient3 = Patient.builder()
                .id(1L)
                .consults(Arrays.asList(consult6, consult7, consult8))
                .build();

        consult6.setPatient(patient3);
        consult7.setPatient(patient3);
        consult8.setPatient(patient3);

        when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2, patient3));
        Patient result = patientService.getLongestHospitalizedPatient();

        assertEquals(patient1.getId(), result.getId());
        assertEquals(patient1.getFirstName(), result.getFirstName());
        assertEquals(patient1.getLastName(), result.getLastName());
    }

    @Test
    @DisplayName("There is no hospitalized patient")
    void getLongestHospitalizedPatientIsNull() {

        when(patientRepository.findAll()).thenReturn(null);
        Patient result = patientService.getLongestHospitalizedPatient();

        assertNull(result);
    }

    @Test
    @DisplayName("Save patient")
    void savePatient() {
        Long patientId = 1L;
        String firstName = "First name";
        String lastName = "Last name";

        Patient patientToBeSaved = Patient.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Patient patientSaved = Patient.builder()
                .id(patientId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        when(patientRepository.save(patientToBeSaved)).thenReturn(patientSaved);

        Patient result = patientService.savePatient(patientToBeSaved);

        assertEquals(patientSaved.getId(), result.getId());
        assertEquals(patientSaved.getFirstName(), result.getFirstName());
        assertEquals(patientSaved.getLastName(), result.getLastName());
    }

    @Test
    @DisplayName("Update patient")
    void updateDepartment() {
        Long patientId = 1L;
        String firstName = "First name";
        String lastName = "Last name";
        String firstNameUpdated = "First name updated";
        String lastNameUpdated = "Last name updated";

        ReqPatientDtoPatch reqPatientDtoPatch = new ReqPatientDtoPatch();
        reqPatientDtoPatch.setFirstName(firstNameUpdated);
        reqPatientDtoPatch.setLastName(lastNameUpdated);

        Patient patientToBeUpdated = Patient.builder()
                .id(patientId)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Patient patientUpdated = Patient.builder()
                .id(patientId)
                .firstName(firstNameUpdated)
                .lastName(lastNameUpdated)
                .build();

        Patient patientSaved = Patient.builder()
                .id(patientId)
                .firstName(firstNameUpdated)
                .lastName(lastNameUpdated)
                .build();


        when(patientMapper.update(reqPatientDtoPatch, patientToBeUpdated)).thenReturn(patientUpdated);

        when(patientRepository.save(any())).thenReturn(patientSaved);

        Patient result = patientService.updatePatient(reqPatientDtoPatch, patientToBeUpdated);

        assertEquals(patientSaved.getId(), result.getId());
        assertEquals(patientSaved.getFirstName(), result.getFirstName());
        assertEquals(patientSaved.getLastName(), result.getLastName());
    }

    @Test
    @DisplayName("Delete patient")
    void deletePatient() {
        Long patientId = 1L;

        doNothing().when(patientRepository).deleteById(patientId);

        patientService.deletePatientById(patientId);

        verify(patientRepository, times(1)).deleteById(patientId);
    }
}
