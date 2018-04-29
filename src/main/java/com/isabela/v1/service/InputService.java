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

    /**
     * add new Input entries in the database
     *
     * @param in
     * @return
     */
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

    /**
     * Get input from bd by docNo
     *
     * @param docNo
     * @return
     */
    public InputDto getInputFor(Long docNo) {
        Input in = new Input();

        in = inputRepository.findByDocNo(docNo);

        InputTransformer transformer = new InputTransformer();

        return transformer.transform(in);
    }

    /**
     * Get Input info by specifications selected by user
     *
     * @param name
     * @param startDate
     * @param endDate
     * @param dueDate
     * @param pageable
     * @return
     */
    public Page<InputDto> searchInputBy(String name, Date startDate, Date endDate, Date dueDate, Pageable pageable) {

        Pageable currentPage = pageable.previousOrFirst();

        Provider provider = new Provider();
        if (name != null) {
            providerRepository.findByName(name);
        }

        Page<Input> inputPage = inputRepository.findAll(
                where(
                        InputSpecification.searchBy(provider.getId(), startDate, endDate, dueDate)
                ), currentPage
        );

        List<Input> inputList = inputPage.getContent();

        List<InputDto> inputDtos = new ArrayList<>();

        InputTransformer transformer = new InputTransformer();

        for (Input in : inputList) {
            InputDto inputDto = transformer.transform(in);
            inputDtos.add(inputDto);
        }
        
        return new PageImpl<InputDto>(inputDtos, currentPage, inputDtos.size());
    }
}
