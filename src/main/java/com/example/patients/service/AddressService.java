package com.example.patients.service;

import com.example.patients.model.Address;
import com.example.patients.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address getById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Boolean checkIfAddressExists(Long id) {
        return addressRepository.findById(id).isPresent();
    }

    public Boolean checkIfAddressIsTakenByPatient(Long addressId) {
        return addressRepository.checkIfAddressIsTakenByPatient(addressId);
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

}
