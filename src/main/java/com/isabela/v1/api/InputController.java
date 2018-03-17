package com.isabela.v1.api;

import com.isabela.v1.api.request.ProviderRequest;
import com.isabela.v1.core.dto.InputDto;
import com.isabela.v1.core.dto.ProviderDto;
import com.isabela.v1.core.model.Address;
import com.isabela.v1.core.model.Input;
import com.isabela.v1.core.model.Provider;
import com.isabela.v1.core.repository.ProviderRepository;
import com.isabela.v1.service.AddressService;
import com.isabela.v1.service.InputService;
import com.isabela.v1.service.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Api(value = "Input")
@RestController
@RequestMapping("/input/v1")
public class InputController {

    @Resource
    private AddressService addressService;

    @Resource
    private ProviderService providerService;

    @Resource
    private InputService inputService;

    @Resource
    private ProviderRepository providerRepository;

    @RequestMapping(value = "/add_provider",
                    method = RequestMethod.POST,
                    produces = "application/json")
    @ApiOperation(value = "Create new provider",
                    tags = "add",
                    response = ProviderDto.class)
    @ApiResponse(code = 200, message = "Success")
    public ProviderDto addProvider(@RequestBody ProviderRequest request){

        Address address = addressService.createAddress(request.getCountry(), request.getCounty(), request.getAddress());

        Provider provider = providerService.createProvider(request.getName(), request.getFiscalCode(), address, request.getBankAccount());

        return new ProviderDto(provider.getId(), provider.getName(), provider.getFiscalCode(), address.getCountry(), address.getCounty(), address.getAddress(), provider.getBankAccount());
    }

    @RequestMapping(value = "/in",
                    method = RequestMethod.POST,
                    produces = "application/json")
    @ApiOperation(value = "Input",
                    tags = "add",
                    response = InputDto.class)
    @ApiResponse(code = 200, message = "Success")
    public InputDto addInput(@ApiParam(allowableValues = "Factura, Bon de casa, Bon de casa cu cof fiscal, Aviz, Taxare inversa")@RequestParam String type,
                             @RequestParam Long docNo,
                             @RequestParam(required = false) Long providerId,
                             @RequestParam(required = false) String providerName,
                             @Valid @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date releaseDate,
                             @Valid @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date dueDate,
                             @RequestParam Double value,
                             @RequestParam Double tva,
                             @RequestParam Double total,
                             @RequestParam Double toPay) {

        providerName = org.apache.commons.lang.StringUtils.trimToNull(providerName);
        Provider provider = new Provider();
        if (providerId != null) {
            provider = providerRepository.findOne(providerId);
        } else if (providerName != null) {
            provider = providerRepository.findByName(providerName);
        }

        InputDto in = new InputDto();
        in.setType(type);
        in.setDocNo(docNo);
        in.setProviderId(provider.getId());
        in.setProviderName(provider.getName());
        in.setReleaseDate(releaseDate);
        if (dueDate != null) {
            in.setDueDate(dueDate);
        } else {
            Long ltime = releaseDate.getTime() + 1*24*60*60*1000;
            in.setDueDate(new Date(ltime));
        }
        in.setValue(value);
        in.setTva(tva);
        in.setTotal(total);
        in.setToPay(toPay);
        Input input = inputService.createInput(in);

        return new InputDto(input.getType(), input.getId(), input.getDocNo(), input.getProviderId(), input.getProvider().getName(), input.getReleaseDate(), input.getDueDate(), input.getValue(), input.getTva(), input.getTotal(), input.getToPay());
    }

    @RequestMapping(value = "/get_input",
                    method = RequestMethod.GET,
                    produces = "application/json")
    @ApiOperation(value = "Input",
                    tags = "get",
                    response = InputDto.class)
    public List<InputDto> getInputForReleaseDate(@RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date dueDate,
                                                 @RequestParam(required = false) String providerName) {

        providerName = org.apache.commons.lang.StringUtils.trimToNull(providerName);

        if (dueDate == null && providerName == null) {
            return inputService.getInputBetween(startDate, endDate);
        }

        if (dueDate == null && providerName != null) {
            return inputService.getInputByProviderAndReleaseDates(providerName, startDate, endDate);
        }

        if (dueDate != null && providerName != null) {
            return inputService.getInputForProviderAndBefore(providerName, dueDate);
        }

        return null;
    }

    @RequestMapping(value = "/get_single_input",
            method = RequestMethod.GET,
            produces = "application/json")
    @ApiOperation(value = "Single Input",
            tags = "get",
            response = InputDto.class)
    public InputDto getSingleInputFor(@RequestParam Long docNo) {
        return inputService.getInputFor(docNo);
    }

}
