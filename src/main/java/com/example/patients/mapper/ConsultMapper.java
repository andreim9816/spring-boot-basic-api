package com.example.patients.mapper;

import com.example.patients.dto.ConsultDto;
import com.example.patients.dto.PatientDto;
import com.example.patients.dto.input.ReqConsultDto;
import com.example.patients.mapper.qualifier.MapperQualifier;
import com.example.patients.mapper.qualifier.PersonQualifier;
import com.example.patients.model.Consult;
import com.example.patients.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {MapperQualifier.class, PersonQualifier.class})
public interface ConsultMapper {

    ConsultDto toDto(Consult entity);

    @Mapping(source = "address.no", target = "address.number")
    PatientDto toDto(Patient entity);

    @Mapping(source = "patientId", target = "patient", qualifiedByName = "idToPatient")
    @Mapping(source = "doctorId", target = "doctor", qualifiedByName = "idToDoctor")
    @Mapping(source = "medicationIds", target = "medications", qualifiedByName = "idsToMedications")
    Consult toEntity(ReqConsultDto dto);

    @Mapping(source = "patientId", target = "patient", qualifiedByName = "idToPatient")
    @Mapping(source = "doctorId", target = "doctor", qualifiedByName = "idToDoctor")
    @Mapping(source = "medicationIds", target = "medications", qualifiedByName = "idsToMedications")
    Consult update(ReqConsultDto req, @MappingTarget Consult entity);
}
