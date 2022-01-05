package com.example.patients.controller;

import com.example.patients.dto.AddressDto;
import com.example.patients.dto.input.ReqAddressDto;
import com.example.patients.mapper.AddressMapper;
import com.example.patients.model.Address;
import com.example.patients.service.AddressService;
import com.example.patients.service.constraint.ValidAddress;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/addresses")
@Validated
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping
    @Operation(
            method = "GET",
            summary = "Get all addresses"
    )
    public ResponseEntity<List<AddressDto>> getAll() {

        List<AddressDto> result = addressService.getAll()
                .stream().map(addressMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/{address-id}")
    @Operation(
            method = "GET",
            summary = "Get an address by ID"
    )
    public ResponseEntity<AddressDto> getById(@PathVariable("address-id") @ValidAddress Long id) {

        AddressDto result = addressMapper.toDto(addressService.getById(id));

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping
    @Operation(
            method = "POST",
            summary = "Save a new address"
    )
    public ResponseEntity<AddressDto> saveAddress(@RequestBody @Valid ReqAddressDto reqAddress) {

        Address address = addressMapper.toEntity(reqAddress);
        Address savedAddress = addressService.save(address);
        AddressDto result = addressMapper.toDto(savedAddress);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping("/{address-id}")
    @Operation(
            method = "PATCH",
            summary = "Update an address"
    )
    public ResponseEntity<AddressDto> updateAddress(@PathVariable("address-id") @ValidAddress Long addressId,
                                                    @RequestBody @Valid ReqAddressDto reqAddress) {

        Address address = addressService.getById(addressId);
        Address updatedAddress = addressMapper.update(reqAddress, address);
        Address savedAddress = addressService.save(updatedAddress);
        AddressDto result = addressMapper.toDto(savedAddress);

        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{address-id}")
    @Operation(
            method = "DELETE",
            summary = "Delete an address"
    )
    public ResponseEntity<?> deleteAddress(@PathVariable("address-id") @ValidAddress Long addressId) {

        addressService.deleteById(addressId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
