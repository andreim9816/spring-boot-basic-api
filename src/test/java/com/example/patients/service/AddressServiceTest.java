package com.example.patients.service;

import com.example.patients.dto.input.ReqAddressDto;
import com.example.patients.exception.EntityNotFoundException;
import com.example.patients.mapper.AddressMapper;
import com.example.patients.model.Address;
import com.example.patients.repository.AddressRepository;
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
class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;

    @Mock
    AddressMapper addressMapper;

    @InjectMocks
    AddressService addressService;

    @Test
    @DisplayName("Get all addresss")
    void getAllAddresss() {
        Address address1 = Address.builder()
                .id(1L)
                .no(1)
                .street("Street one")
                .city("City one")
                .build();

        Address address2 = Address.builder()
                .id(2L)
                .no(2)
                .street("Street two")
                .city("City two")
                .build();

        when(addressRepository.findAll()).thenReturn(Arrays.asList(address1, address2));

        List<Address> result = addressService.getAllAddresses();

        assertEquals(result.get(0), address1);
        assertEquals(result.get(1), address2);
    }

    @Test
    @DisplayName("Get address by ID successful")
    void getAddressById() {
        Long addressId = 1L;
        Address address = Address.builder()
                .id(addressId)
                .no(1)
                .street("Street one")
                .city("City one")
                .build();

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));

        Address result = addressService.getAddressById(addressId);

        assertEquals(address, result);
    }


    @Test
    @DisplayName("Get address by ID FAILED")
    void getAddressByIdFailed() {
        Long addressId = 1L;

        EntityNotFoundException exceptionToBeThrown = EntityNotFoundException.builder()
                .entityId(addressId)
                .entityType("Address")
                .build();

        when(addressRepository.findById(addressId)).thenThrow(exceptionToBeThrown);

        EntityNotFoundException exception = null;

        try {
            addressService.getAddressById(addressId);
        } catch (EntityNotFoundException e) {
            exception = e;
        }

        assertNotNull(exception);
        assertEquals(exceptionToBeThrown.getEntityId(), exception.getEntityId());
        assertEquals(exceptionToBeThrown.getEntityType(), exception.getEntityType());
    }

    @Test
    @DisplayName("Check if addresss exists")
    void checkIfAddressExists() {
        Long addressId = 1L;

        when(addressRepository.findById(addressId)).thenReturn(Optional.of(Address.builder().build()));

        Boolean result = addressService.checkIfAddressExists(addressId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Check if addresss is taken by patient")
    void checkIfAddressIsTakenByPatient() {
        Long addressId = 1L;

        when(addressRepository.checkIfAddressIsTakenByPatient(addressId)).thenReturn(Boolean.TRUE);

        Boolean result = addressService.checkIfAddressIsTakenByPatient(addressId);
        assertTrue(result);
    }

    @Test
    @DisplayName("Save address")
    void saveAddress() {
        Long addressId = 1L;
        String street = "First name";
        String city = "Last name";
        Integer no = 1;

        Address addressToBeSaved = Address.builder()
                .id(addressId)
                .no(no)
                .street(street)
                .city(city)
                .build();

        Address addressSaved = Address.builder()
                .id(addressId)
                .no(no)
                .street(street)
                .city(city)
                .build();

        when(addressRepository.save(addressToBeSaved)).thenReturn(addressSaved);

        Address result = addressService.saveAddress(addressToBeSaved);

        assertEquals(addressSaved.getId(), result.getId());
        assertEquals(addressSaved.getCity(), result.getCity());
        assertEquals(addressSaved.getNo(), result.getNo());
        assertEquals(addressSaved.getStreet(), result.getStreet());
    }

    @Test
    @DisplayName("Update address")
    void updateDepartment() {
        Long addressId = 1L;

        String street = "Street";
        String city = "City";
        Integer no = 1;

        String streetUpdated = "Street updated";
        String cityUpdated = "City updated";
        Integer noUpdated = 2;


        ReqAddressDto reqAddressDto = new ReqAddressDto();
        reqAddressDto.setCity(cityUpdated);
        reqAddressDto.setNumber(noUpdated);
        reqAddressDto.setStreet(streetUpdated);

        Address addressToBeUpdated = Address.builder()
                .id(addressId)
                .no(no)
                .street(street)
                .city(city)
                .build();

        Address addressUpdated = Address.builder()
                .id(addressId)
                .no(noUpdated)
                .street(streetUpdated)
                .city(cityUpdated)
                .build();

        Address addressSaved = Address.builder()
                .id(addressId)
                .no(noUpdated)
                .street(streetUpdated)
                .city(cityUpdated)
                .build();


        when(addressMapper.update(reqAddressDto, addressToBeUpdated)).thenReturn(addressUpdated);

        when(addressRepository.save(any())).thenReturn(addressSaved);

        Address result = addressService.updateAddress(reqAddressDto, addressToBeUpdated);

        assertEquals(addressSaved, result);
    }

    @Test
    @DisplayName("Delete address")
    void deleteAddress() {
        Long addressId = 1L;

        doNothing().when(addressRepository).deleteById(addressId);

        addressService.deleteAddressById(addressId);

        verify(addressRepository, times(1)).deleteById(addressId);
    }
}
