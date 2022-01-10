package com.example.patients.service;

import com.example.patients.dto.input.ReqConsultDto;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.ConsultMapper;
import com.example.patients.model.Consult;
import com.example.patients.repository.ConsultRepository;
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
class ConsultServiceTest {

    @Mock
    ConsultRepository consultRepository;

    @Mock
    ConsultMapper consultMapper;

    @InjectMocks
    ConsultService consultService;

    @Test
    @DisplayName("Get all consults")
    void getAllConsults() {
        Consult consult1 = Consult.builder()
                .id(1L)
                .date(new Date())
                .comment("Comment 1")
                .diagnose("Diagnose 1")
                .build();

        Consult consult2 = Consult.builder()
                .id(2L)
                .date(new Date())
                .comment("Comment 2")
                .diagnose("Diagnose 2")
                .build();

        when(consultRepository.findAll()).thenReturn(Arrays.asList(consult1, consult2));

        List<Consult> result = consultService.getAllConsults();

        assertEquals(consult1.getId(), result.get(0).getId());
        assertEquals(consult1.getDiagnose(), result.get(0).getDiagnose());
        assertEquals(consult1.getComment(), result.get(0).getComment());

        assertEquals(consult2.getId(), result.get(1).getId());
        assertEquals(consult2.getDiagnose(), result.get(1).getDiagnose());
        assertEquals(consult2.getComment(), result.get(1).getComment());
    }

    @Test
    @DisplayName("Get consult by ID successful")
    void getConsultById() {
        Long consultId = 1L;
        Consult consult = Consult.builder()
                .id(1L)
                .date(new Date())
                .comment("Comment 1")
                .diagnose("Diagnose 1")
                .build();


        when(consultRepository.findById(consultId)).thenReturn(Optional.of(consult));

        Consult result = consultService.getConsultById(consultId);

        assertEquals(consult.getId(), result.getId());
        assertEquals(consult.getComment(), result.getComment());
        assertEquals(consult.getDiagnose(), result.getDiagnose());
    }

    @Test
    @DisplayName("Get consult by ID FAILED")
    void getConsultByIdFailed() {
        Long consultId = 1L;

        EntityNotFoundException exceptionToBeThrown = EntityNotFoundException.builder()
                .entityId(consultId)
                .entityType("Consult")
                .build();

        when(consultRepository.findById(consultId)).thenThrow(exceptionToBeThrown);

        EntityNotFoundException exception = null;

        try {
            consultService.getConsultById(consultId);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(exceptionToBeThrown.getEntityId(), exception.getEntityId());
        assertEquals(exceptionToBeThrown.getEntityType(), exception.getEntityType());
    }

    @Test
    @DisplayName("Get consults between doctor and patient")
    void getAllConsultsForDoctorAndPatient() {
        Long doctorId = 1L;
        Long patientId = 2L;

        Consult consult1 = Consult.builder()
                .id(1L)
                .date(new Date())
                .comment("Comment 1")
                .diagnose("Diagnose 1")
                .build();

        Consult consult2 = Consult.builder()
                .id(2L)
                .date(new Date())
                .comment("Comment 2")
                .diagnose("Diagnose 2")
                .build();

        when(consultRepository.getConsultsByDoctorIdAndPatientId(doctorId, patientId)).thenReturn(List.of(consult1, consult2));

        List<Consult> result = consultService.getAllConsultsForDoctorAndPatient(doctorId, patientId);

        assertEquals(consult1.getId(), result.get(0).getId());
        assertEquals(consult1.getComment(), result.get(0).getComment());
        assertEquals(consult1.getDiagnose(), result.get(0).getDiagnose());

        assertEquals(consult2.getId(), result.get(1).getId());
        assertEquals(consult2.getComment(), result.get(1).getComment());
        assertEquals(consult2.getDiagnose(), result.get(1).getDiagnose());
    }

    @Test
    @DisplayName("Check if consult exists")
    void checkIfConsultExists() {
        Long consultId = 1L;

        when(consultRepository.findById(consultId)).thenReturn(Optional.of(new Consult()));

        Boolean result = consultService.checkIfConsultExists(consultId);

        assertTrue(result);
    }

    @Test
    @DisplayName("Check if consult exists FAILED")
    void checkIfConsultExistsFailed() {
        Long consultId = 1L;

        when(consultRepository.findById(consultId)).thenReturn(Optional.empty());

        Boolean result = consultService.checkIfConsultExists(consultId);

        assertFalse(result);
    }

    @Test
    @DisplayName("Update consult")
    void updateConsult() {
        Long consultId = 1L;
        String consultComment = "Consult comment";
        String consultCommentUpdated = "Consult comment updated";

        String diagnose = "Diagnose";
        String diagnoseUpdated = "Diagnose updated";

        ReqConsultDto reqConsultDto = new ReqConsultDto();
        reqConsultDto.setComment(consultCommentUpdated);
        reqConsultDto.setDiagnose(diagnoseUpdated);

        Consult consultToBeUpdated = Consult.builder()
                .id(consultId)
                .comment(consultComment)
                .diagnose(diagnose)
                .build();

        Consult consultUpdated = Consult.builder()
                .id(consultId)
                .comment(consultCommentUpdated)
                .diagnose(diagnoseUpdated)
                .build();

        Consult consultSaved = Consult.builder()
                .id(consultId)
                .comment(consultCommentUpdated)
                .diagnose(diagnoseUpdated)
                .build();


        when(consultMapper.update(reqConsultDto, consultToBeUpdated)).thenReturn(consultUpdated);
        when(consultRepository.save(any())).thenReturn(consultSaved);

        Consult result = consultService.updateConsult(reqConsultDto, consultToBeUpdated);

        assertEquals(consultSaved.getId(), result.getId());
        assertEquals(consultSaved.getComment(), result.getComment());
        assertEquals(consultSaved.getDiagnose(), result.getDiagnose());
    }

    @Test
    @DisplayName("Save consult")
    void saveConsult() {
        Long consultId = 1L;
        String consultComment = "Consult comment";
        String diagnose = "Diagnose";

        Consult consultToBeSaved = Consult.builder()
                .comment(consultComment)
                .diagnose(diagnose)
                .build();

        Consult consultSaved = Consult.builder()
                .id(consultId)
                .comment(consultComment)
                .diagnose(diagnose)
                .build();

        when(consultRepository.save(consultToBeSaved)).thenReturn(consultSaved);

        Consult result = consultService.saveConsult(consultToBeSaved);

        assertEquals(consultSaved.getId(), result.getId());
        assertEquals(consultSaved.getComment(), result.getComment());
        assertEquals(consultSaved.getDiagnose(), result.getDiagnose());
    }

    @Test
    @DisplayName("Delete consult")
    void deleteConsult() {
        Long consultId = 1L;

        doNothing().when(consultRepository).deleteById(consultId);

        consultService.deleteConsultById(consultId);

        verify(consultRepository, times(1)).deleteById(consultId);
    }
}
