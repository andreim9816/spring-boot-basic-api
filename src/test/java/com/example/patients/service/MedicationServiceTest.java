package com.example.patients.service;

import com.example.patients.dto.input.ReqMedicationDto;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.MedicationMapper;
import com.example.patients.model.Medication;
import com.example.patients.repository.MedicationRepository;
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
class MedicationServiceTest {

    @Mock
    MedicationRepository medicationRepository;

    @Mock
    MedicationMapper medicationMapper;

    @InjectMocks
    MedicationService medicationService;

    @Test
    @DisplayName("Get all medications")
    void getAllMedications() {
        Medication medication1 = Medication.builder()
                .id(1L)
                .name("Medication 1")
                .quantity(20)
                .build();

        Medication medication2 = Medication.builder()
                .id(2L)
                .name("Medication 2")
                .quantity(5)
                .build();

        when(medicationRepository.findAll()).thenReturn(Arrays.asList(medication1, medication2));

        List<Medication> result = medicationService.getAllMedications();

        assertEquals(result.get(0), medication1);
        assertEquals(result.get(1), medication2);
    }

    @Test
    @DisplayName("Get medication by ID successful")
    void getMedicationById() {
        Long medicationId = 1L;
        Medication medication = Medication.builder()
                .id(medicationId)
                .name("Medication 1")
                .quantity(20)
                .build();

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medication));

        Medication result = medicationService.getMedicationById(medicationId);

        assertEquals(medication, result);
    }

    @Test
    @DisplayName("Get medication by ID FAILED")
    void getMedicationByIdFailed() {
        Long medicationId = 1L;

        EntityNotFoundException exceptionToBeThrown = EntityNotFoundException.builder()
                .entityId(medicationId)
                .entityType("Medication")
                .build();

        when(medicationRepository.findById(medicationId)).thenThrow(exceptionToBeThrown);

        EntityNotFoundException exception = null;

        try {
            medicationService.getMedicationById(medicationId);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(exceptionToBeThrown.getEntityId(), exception.getEntityId());
        assertEquals(exceptionToBeThrown.getEntityType(), exception.getEntityType());
    }

    @Test
    @DisplayName("Check if medication exists")
    void checkIfMedicationExists() {
        Long medicationId = 1L;

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(new Medication()));

        Boolean result = medicationService.checkIfMedicationExists(medicationId);

        assertTrue(result);
    }

    @Test
    @DisplayName("Check if medication exists FAILED")
    void checkIfMedicationExistsFailed() {
        Long medicationId = 1L;

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.empty());

        Boolean result = medicationService.checkIfMedicationExists(medicationId);

        assertFalse(result);
    }

    @Test
    @DisplayName("Update medication")
    void updateMedication() {
        Long medicationId = 1L;
        String medicationName = "Medication name";
        String medicationNameUpdated = "New medication name";

        ReqMedicationDto reqMedicationDto = new ReqMedicationDto();
        reqMedicationDto.setName(medicationNameUpdated);

        Medication medicationToBeUpdated = Medication.builder()
                .id(medicationId)
                .name(medicationName)
                .build();

        Medication medicationUpdated = Medication.builder()
                .id(medicationId)
                .name(medicationNameUpdated)
                .build();

        Medication medicationSaved = Medication.builder()
                .id(medicationId)
                .name(medicationNameUpdated)
                .build();


        when(medicationMapper.update(reqMedicationDto, medicationToBeUpdated)).thenReturn(medicationUpdated);
        when(medicationRepository.save(any())).thenReturn(medicationSaved);

        Medication result = medicationService.updateMedication(reqMedicationDto, medicationToBeUpdated);

        assertEquals(medicationSaved, result);
    }

    @Test
    @DisplayName("Save medication")
    void saveMedication() {
        Long medicationId = 1L;
        String medicationName = "Medication name";
        Integer quantity = 300;

        Medication medicationToBeSaved = Medication.builder()
                .name(medicationName)
                .quantity(quantity)
                .build();

        Medication medicationSaved = Medication.builder()
                .id(medicationId)
                .name(medicationName)
                .quantity(quantity)
                .build();

        when(medicationRepository.save(medicationToBeSaved)).thenReturn(medicationSaved);

        Medication result = medicationService.saveMedication(medicationToBeSaved);

        assertEquals(medicationSaved.getId(), result.getId());
        assertEquals(medicationSaved.getName(), result.getName());
    }

    @Test
    @DisplayName("Delete medication")
    void deleteMedication() {
        Long medicationId = 1L;

        doNothing().when(medicationRepository).deleteById(medicationId);

        medicationService.deleteMedicationById(medicationId);

        verify(medicationRepository, times(1)).deleteById(medicationId);
    }
}
