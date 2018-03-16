package com.isabela.v1.service;

import com.isabela.v1.core.model.Address;
import com.isabela.v1.core.model.Provider;
import com.isabela.v1.core.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Component
@Service
@Transactional
public class ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    public Provider createProvider(String name, String fiscalCode, Address address, String bankAccount) {

        Provider provider = new Provider();
        provider.setName(name);
        provider.setFiscalCode(fiscalCode);
        provider.setAddress(address);
        provider.setBankAccount(bankAccount);

        providerRepository.save(provider);
        return provider;
    }
}
