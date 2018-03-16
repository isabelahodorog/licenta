package com.isabela.v1.service;

import com.isabela.v1.core.dto.InputDto;
import com.isabela.v1.core.model.Input;
import com.isabela.v1.core.repository.InputRepository;
import com.isabela.v1.core.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Component
@Service
@Transactional
public class InputService {

    @Autowired
    private InputRepository inputRepository;

    @Autowired
    private ProviderRepository providerRepository;

    public Input createInput(InputDto in) {

        Input input = new Input();

        input.setType(in.getType());
        input.setDocNo(in.getDocNo());
        input.setProvider(providerRepository.findOne(in.getProviderId()));
        input.setReleaseDate(in.getReleaseDate());
        input.setDueDate(in.getDueDate());
        input.setValue(in.getValue());
        input.setTva(in.getTva());
        input.setTotal(in.getTotal());
        input.setToPay(in.getToPay());

        inputRepository.save(input);
        return input;
    }
}
