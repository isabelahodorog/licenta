package com.isabela.v1.service;

import com.isabela.v1.core.dto.ProviderDto;
import com.isabela.v1.core.model.Address;
import com.isabela.v1.core.model.Provider;
import com.isabela.v1.core.repository.ProviderRepository;
import com.isabela.v1.core.transformer.ProviderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    public ProviderDto getProviderWith(String name, Long id) {

        Provider provider = new Provider();

        if (id != null) {
            provider = providerRepository.findOne(id);
        } else {
            provider = providerRepository.findByName(name);
        }

        ProviderTransformer transformer = new ProviderTransformer();

        return transformer.transform(provider);
    }

    public Page<ProviderDto> getProvider(Pageable pageable) {

        Pageable currentPage = pageable.previousOrFirst();

        List<Provider> providers = providerRepository.findAll();
        List<ProviderDto> providerDtos = new ArrayList<>();
        ProviderTransformer transformer = new ProviderTransformer();

        for (Provider provider : providers) {
            providerDtos.add(transformer.transform(provider));
        }

        int start = currentPage.getOffset();
        int end = (start + currentPage.getPageSize()) > providerDtos.size() ? providerDtos.size() : (start + currentPage.getPageSize());
        Page<ProviderDto> pages = new PageImpl<ProviderDto>(providerDtos.subList(start, end), currentPage, providerDtos.size());
        return pages;
    }
}
