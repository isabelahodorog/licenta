package com.isabela.v1.service;

import com.isabela.v1.core.model.Address;
import com.isabela.v1.core.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Component
@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(String country, String county, String address) {

        Address newAddress = new Address();
        newAddress.setCounty(county);
        newAddress.setCountry(country);
        newAddress.setAddress(address);

        addressRepository.save(newAddress);
        return newAddress;
    }

}
