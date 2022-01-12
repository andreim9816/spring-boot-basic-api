package com.example.patients.controller;

import com.example.patients.dto.input.ReqConsultDto;
import com.example.patients.mapper.ConsultMapper;
import com.example.patients.model.Consult;
import com.example.patients.model.Doctor;
import com.example.patients.model.Patient;
import com.example.patients.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConsultController.class)
@ComponentScan(basePackageClasses = {ConsultMapper.class})
class ConsultControllerIT {

    private static Consult consult1, consult2;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ConsultService consultService;
    @Autowired
    private ConsultMapper consultMapper;
    @MockBean
    private AddressService addressService;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private MedicationService medicationService;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private PatientService patientService;

    @BeforeAll
    static void createConsultObject() {
        Long consult1Id = 1L;
        Long consult2Id = 2L;

        String comment1 = "Comment 1";
        String comment2 = "Comment 2";

        String diagnose1 = "Diagnose 1";
        String diagnose2 = "Diagnose 2";

        Long patientId = 1L;
        Long doctorId = 2L;

        Patient patient = Patient.builder()
                .id(patientId)
                .build();

        Doctor doctor = Doctor.builder()
                .id(doctorId)
                .build();

        consult1 = Consult.builder()
                .id(consult1Id)
                .date(new Date())
                .comment(comment1)
                .diagnose(diagnose1)
                .patient(patient)
                .doctor(doctor)
                .build();

        consult2 = Consult.builder()
                .id(consult2Id)
                .date(new Date())
                .comment(comment2)
                .diagnose(diagnose2)
                .patient(patient)
                .doctor(doctor)
                .build();
    }

    @Test
    @DisplayName("Get all consults")
    void getAllConsults() throws Exception {
        when(consultService.getAllConsults()).thenReturn(Arrays.asList(consult1, consult2));

        mockMvc.perform(get("/consults")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(consult1.getId()))
                .andExpect(jsonPath("$[0].comment").value(consult1.getComment()))
                .andExpect(jsonPath("$[0].diagnose").value(consult1.getDiagnose()))
                .andExpect(jsonPath("$[1].id").value(consult2.getId()))
                .andExpect(jsonPath("$[1].comment").value(consult2.getComment()))
                .andExpect(jsonPath("$[1].diagnose").value(consult2.getDiagnose()));
    }

    @Test
    @DisplayName("Get all consults for doctor and patient")
    void getAllConsultsForDoctorAndPatient() throws Exception {

        Long patientId = consult1.getPatient().getId();
        Long doctorId = consult1.getDoctor().getId();

        when(doctorService.checkIfDoctorExists(doctorId)).thenReturn(true);
        when(patientService.checkIfPatientExists(patientId)).thenReturn(true);
        when(consultService.getAllConsultsForDoctorAndPatient(doctorId, patientId)).thenReturn(Arrays.asList(consult1, consult2));

        mockMvc.perform(get("/consults/filtered")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorId", String.valueOf(doctorId))
                        .param("patientId", String.valueOf(patientId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(consult1.getId()))
                .andExpect(jsonPath("$[0].comment").value(consult1.getComment()))
                .andExpect(jsonPath("$[0].diagnose").value(consult1.getDiagnose()))
                .andExpect(jsonPath("$[1].id").value(consult2.getId()))
                .andExpect(jsonPath("$[1].comment").value(consult2.getComment()))
                .andExpect(jsonPath("$[1].diagnose").value(consult2.getDiagnose()));
    }

    @Test
    @DisplayName("Get all consults for doctor and patient fails at patient validation")
    void getAllConsultsForDoctorAndPatient_PatientFailure() throws Exception {

        Long patientId = consult1.getPatient().getId();
        Long doctorId = consult1.getDoctor().getId();

        when(doctorService.checkIfDoctorExists(doctorId)).thenReturn(true);
        when(consultService.getAllConsultsForDoctorAndPatient(doctorId, patientId)).thenReturn(Arrays.asList(consult1, consult2));

        mockMvc.perform(get("/consults/filtered")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorId", String.valueOf(doctorId))
                        .param("patientId", String.valueOf(patientId)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get all consults for doctor and patient fails at doctor validation")
    void getAllConsultsForDoctorAndPatient_DoctorFailure() throws Exception {

        Long patientId = consult1.getPatient().getId();
        Long doctorId = consult1.getDoctor().getId();

        when(patientService.checkIfPatientExists(patientId)).thenReturn(true);
        when(consultService.getAllConsultsForDoctorAndPatient(doctorId, patientId)).thenReturn(Arrays.asList(consult1, consult2));

        mockMvc.perform(get("/consults/filtered")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorId", String.valueOf(doctorId))
                        .param("patientId", String.valueOf(patientId)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get consult by ID")
    void getConsultById() throws Exception {

        Long patientId = consult1.getPatient().getId();
        Long doctorId = consult1.getDoctor().getId();

        when(consultService.checkIfConsultExists(consult1.getId())).thenReturn(true);
        when(consultService.getConsultById(consult1.getId())).thenReturn(consult1);

        mockMvc.perform(get("/consults/{consult-id}", consult1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(consult1.getId()))
                .andExpect(jsonPath("$.comment").value(consult1.getComment()))
                .andExpect(jsonPath("$.diagnose").value(consult1.getDiagnose()))
                .andExpect(jsonPath("$.patient.id").value(patientId))
                .andExpect(jsonPath("$.doctor.id").value(doctorId));
    }

    @Test
    @DisplayName("Get consult by ID fails at validation")
    void getConsultById_Failure() throws Exception {
        when(consultService.getConsultById(consult1.getId())).thenReturn(consult1);

        mockMvc.perform(get("/consults/{consult-id}", consult1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Save consult")
    void saveConsult() throws Exception {
        Long newPatientId = 10L;
        Long newDoctorId = 11L;
        String newDiagnose = "New diagnose";
        String newComment = "New comment";
        String newSymptoms = "New symptoms";

        Patient newPatient = Patient.builder()
                .id(newPatientId)
                .build();

        Doctor newDoctor = Doctor.builder()
                .id(newDoctorId)
                .build();

        ReqConsultDto request = new ReqConsultDto();
        request.setDiagnose(newDiagnose);
        request.setComment(newComment);
        request.setDoctorId(newDoctorId);
        request.setPatientId(newPatientId);
        request.setSymptoms(newSymptoms);

        Consult result = Consult.builder()
                .id(consult1.getId())
                .comment(newComment)
                .diagnose(newDiagnose)
                .doctor(newDoctor)
                .patient(newPatient)
                .build();

        when(doctorService.checkIfDoctorExists(newDoctorId)).thenReturn(true);
        when(patientService.checkIfPatientExists(newPatientId)).thenReturn(true);
        when(consultService.saveConsult(any())).thenReturn(result);

        mockMvc.perform(post("/consults")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(consult1.getId()))
                .andExpect(jsonPath("$.comment").value(newComment))
                .andExpect(jsonPath("$.diagnose").value(newDiagnose))
                .andExpect(jsonPath("$.doctor.id").value(newDoctorId))
                .andExpect(jsonPath("$.patient.id").value(newPatientId));
    }

    @Test
    @DisplayName("Update consult")
    void updateConsult() throws Exception {
        Long newPatientId = 10L;
        Long newDoctorId = 11L;
        String newDiagnose = "New diagnose";
        String newComment = "New comment";
        String newSymptoms = "New symptoms";

        Patient newPatient = Patient.builder()
                .id(newPatientId)
                .build();

        Doctor newDoctor = Doctor.builder()
                .id(newDoctorId)
                .build();

        ReqConsultDto request = new ReqConsultDto();
        request.setDiagnose(newDiagnose);
        request.setComment(newComment);
        request.setDoctorId(newDoctorId);
        request.setPatientId(newPatientId);
        request.setSymptoms(newSymptoms);

        Consult result = Consult.builder()
                .id(consult1.getId())
                .comment(newComment)
                .diagnose(newDiagnose)
                .doctor(newDoctor)
                .patient(newPatient)
                .build();

        when(consultService.checkIfConsultExists(consult1.getId())).thenReturn(true);
        when(consultService.getConsultById(consult1.getId())).thenReturn(consult1);
        when(doctorService.checkIfDoctorExists(newDoctorId)).thenReturn(true);
        when(patientService.checkIfPatientExists(newPatientId)).thenReturn(true);
        when(consultService.updateConsult(request, consult1)).thenReturn(result);

        mockMvc.perform(put("/consults/{consult-id}", consult1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(consult1.getId()))
                .andExpect(jsonPath("$.comment").value(newComment))
                .andExpect(jsonPath("$.diagnose").value(newDiagnose))
                .andExpect(jsonPath("$.doctor.id").value(newDoctorId))
                .andExpect(jsonPath("$.patient.id").value(newPatientId));
    }

    @Test
    @DisplayName("Delete consult")
    void deleteConsult() throws Exception {

        when(consultService.checkIfConsultExists(consult1.getId())).thenReturn(true);
        doNothing().when(consultService).deleteConsultById(consult1.getId());

        mockMvc.perform(delete("/consults/{consult-id}", consult1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete consult fails at validation")
    void deleteConsult_Fail() throws Exception {

        when(consultService.checkIfConsultExists(consult1.getId())).thenReturn(true);
        doNothing().when(consultService).deleteConsultById(consult1.getId());

        mockMvc.perform(delete("/consults/{consult-id}", consult1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
