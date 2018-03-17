package com.isabela.v1.service;

import com.isabela.v1.core.dto.InputDto;
import com.isabela.v1.core.model.Input;
import com.isabela.v1.core.repository.InputRepository;
import com.isabela.v1.core.repository.ProviderRepository;
import com.isabela.v1.core.transformer.InputTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<InputDto> getInputBetween(Date startDate, Date endDate) {

        List<Input> input = new ArrayList<>();

        if (startDate == null && endDate == null) {
            input = inputRepository.findAll();
        } else if (endDate == null) {
            input = inputRepository.findAllByReleaseDateAfter(startDate);
        } else {
            input = inputRepository.findAllByReleaseDateBetween(startDate, endDate);
        }

        List<InputDto> inputDtos = new ArrayList<>();

        InputTransformer transformer = new InputTransformer();

        for (Input in : input) {
            InputDto inputDto = transformer.transform(in);
            inputDtos.add(inputDto);
        }
        return inputDtos;
    }

    public InputDto getInputFor(Long docNo) {
        Input in = new Input();

        in = inputRepository.findByDocNo(docNo);

        InputTransformer transformer = new InputTransformer();

        return transformer.transform(in);
    }

    public List<InputDto> getInputForProviderAndBefore(String name, Date dueDate) {

        List<Input> input = new ArrayList<>();

        if (dueDate != null && name != null) {
            input = inputRepository.findAllByProviderIdAndDueDateBefore(providerRepository.findByName(name).getId(), dueDate);
        } else if (name == null){
            input = inputRepository.findAllByDueDateBefore(dueDate);
        } else {
            input = inputRepository.findAllByProviderId(providerRepository.findByName(name).getId());
        }

        List<InputDto> inputDtos = new ArrayList<>();

        InputTransformer transformer = new InputTransformer();

        for (Input in : input) {
            InputDto inputDto = transformer.transform(in);
            inputDtos.add(inputDto);
        }
        return inputDtos;
    }

    public List<InputDto> getInputByProviderAndReleaseDates(String name, Date startDate, Date endDate) {

        List<Input> input = new ArrayList<>();

        if (startDate == null) {
            input = inputRepository.findAllByProviderIdAndReleaseDateAfter(providerRepository.findByName(name).getId(), startDate);
        } else {
            input = inputRepository.findAllByProviderIdAndReleaseDateBetween(providerRepository.findByName(name).getId(), startDate, endDate);
        }

        List<InputDto> inputDtos = new ArrayList<>();

        InputTransformer transformer = new InputTransformer();

        for (Input in : input) {
            InputDto inputDto = transformer.transform(in);
            inputDtos.add(inputDto);
        }
        return inputDtos;
    }
}
