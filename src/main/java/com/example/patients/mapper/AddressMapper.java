package com.example.patients.mapper;

import com.example.patients.dto.AddressDto;
import com.example.patients.dto.input.ReqAddressDto;
import com.example.patients.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AddressMapper {

    @Mapping(source = "no", target = "number")
    AddressDto toDto(Address entity);

    @Mapping(source = "number", target = "no")
    Address toEntity(ReqAddressDto dto);

    @Mapping(source = "number", target = "no")
    Address update(ReqAddressDto req, @MappingTarget Address entity);

}
