package com.isabela.v1.service;

import com.isabela.v1.core.dto.InputDto;
import com.isabela.v1.core.model.Input;
import com.isabela.v1.core.model.Provider;
import com.isabela.v1.core.repository.InputRepository;
import com.isabela.v1.core.repository.InputSpecification;
import com.isabela.v1.core.repository.ProviderRepository;
import com.isabela.v1.core.transformer.InputTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

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

    public Page<InputDto> getInputBetween(Date startDate, Date endDate, Pageable pageable) {

        Pageable currentPage = pageable.previousOrFirst();

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

        int start = currentPage.getOffset();
        int end = (start + currentPage.getPageSize() > inputDtos.size()? inputDtos.size() : currentPage.getPageSize());
        Page<InputDto> inputDtoPage = new PageImpl<InputDto>(inputDtos.subList(start, end), currentPage, inputDtos.size());
        return inputDtoPage;
    }

    public InputDto getInputFor(Long docNo) {
        Input in = new Input();

        in = inputRepository.findByDocNo(docNo);

        InputTransformer transformer = new InputTransformer();

        return transformer.transform(in);
    }

    public Page<InputDto> getInputForProviderAndBefore(String name, Date dueDate, Pageable pageable) {

        Pageable currentPage = pageable.previousOrFirst();
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

        int start = currentPage.getOffset();
        int end = (start + currentPage.getPageSize() > inputDtos.size()? inputDtos.size() : currentPage.getPageSize());
        Page<InputDto> inputDtoPage = new PageImpl<InputDto>(inputDtos.subList(start, end), currentPage, inputDtos.size());
        return inputDtoPage;
    }

    public Page<InputDto> searchInputBy(String name, Date startDate, Date endDate, Date dueDate, Pageable pageable) {

        Pageable currentPage = pageable.previousOrFirst();
        List<Input> inputList = new ArrayList<>();

        Provider provider = new Provider();
        if (name != null) {
            providerRepository.findByName(name);
        }

        inputList = inputRepository.findAll(
                where(
                        InputSpecification.searchBy(provider.getId(), startDate, endDate, dueDate)
                )
        );

        List<InputDto> inputDtos = new ArrayList<>();

        InputTransformer transformer = new InputTransformer();

        for (Input in : inputList) {
            InputDto inputDto = transformer.transform(in);
            inputDtos.add(inputDto);
        }

        int start = currentPage.getOffset();
        int end = (start + currentPage.getPageSize() > inputDtos.size()? inputDtos.size() : currentPage.getPageSize());
        Page<InputDto> inputDtoPage = new PageImpl<InputDto>(inputDtos.subList(start, end), currentPage, inputDtos.size());
        return inputDtoPage;
    }
}
