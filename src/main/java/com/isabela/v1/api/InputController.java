package com.isabela.v1.api;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

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
    public ProviderDto addProvider(@RequestParam String name,
                                   @RequestParam String fiscalCode,
                                   @RequestParam String country,
                                   @RequestParam String county,
                                   @RequestParam String address,
                                   @RequestParam String bankAccount){

        Address address1 = addressService.createAddress(country, county, address);

        Provider provider = providerService.createProvider(name, fiscalCode, address1, bankAccount);

        return new ProviderDto(provider.getId(), provider.getName(), provider.getFiscalCode(), address1.getCountry(), address1.getCounty(), address1.getAddress(), provider.getBankAccount());
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
                    response = Page.class)
    @ApiResponse(code = 200, message = "Success")
    public Page<InputDto> getInputForReleaseDate(@RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate,
                                                 @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date dueDate,
                                                 @RequestParam(required = false) String providerName,
                                                 @PageableDefault(page = 0, size = 100) Pageable pageable) {

        providerName = org.apache.commons.lang.StringUtils.trimToNull(providerName);

        return inputService.searchInputBy(providerName, startDate, endDate, dueDate, pageable);
    }

    @RequestMapping(value = "/get_single_input",
            method = RequestMethod.GET,
            produces = "application/json")
    @ApiOperation(value = "Single Input",
            tags = "get",
            response = InputDto.class)
    @ApiResponse(code = 200, message = "Success")
    public InputDto getSingleInputFor(@RequestParam Long docNo) {
        return inputService.getInputFor(docNo);
    }

    @RequestMapping(value = "/get_single_provider",
            method = RequestMethod.GET,
            produces = "application/json")
    @ApiOperation(value = "Single Provider",
            tags = "get",
            response = ProviderDto.class)
    @ApiResponse(code = 200, message = "Success")
    public ProviderDto getSingleProviderFor(@RequestParam(required = false) Long id,
                                            @RequestParam(required = false) String name) {

        name = org.apache.commons.lang.StringUtils.trimToNull(name);
        return providerService.getProviderWith(name, id);
    }

    @RequestMapping(value = "/get_provider",
                    method = RequestMethod.GET,
                    produces = "application/json")
    @ApiOperation(value = "Get Providers",
            tags = "get",
            response = Page.class)
    @ApiResponse(code = 200, message = "Success")
    public Page<ProviderDto> getProvider(@PageableDefault(page = 0, size = 100) Pageable pageable){
        return providerService.getProvider(pageable);
    }
}
