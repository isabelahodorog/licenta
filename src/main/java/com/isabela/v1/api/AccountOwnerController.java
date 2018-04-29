package com.isabela.v1.api;

import com.isabela.exception.IncorrectDataException;
import com.isabela.exception.InvalidEmailException;
import com.isabela.v1.core.dto.ProviderDto;
import com.isabela.v1.core.model.AccountOwner;
import com.isabela.v1.service.AccountOwnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "AccountOwner")
@RestController
@RequestMapping("licenta/v1")
public class AccountOwnerController {

    @Resource
    AccountOwnerService accountOwnerService;

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = "application/json")
    @ApiOperation(value = "register",
            tags = "create entry",
            response = ProviderDto.class)
    @ApiResponse(code = 200, message = "Success")
    public AccountOwner register(@RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String confirmPassword) throws Exception {

        name = StringUtils.trimToNull(name);
        email = StringUtils.trimToNull(email);
        password = StringUtils.trimToNull(password);
        confirmPassword = StringUtils.trimToNull(confirmPassword);

        if(!email.matches(".+@.+\\..+")) {
            throw new InvalidEmailException("Email is incorrect!");
        }

        if (password.equals(confirmPassword)){
            return accountOwnerService.createOwner(name, email, password);
        } else {
            throw new IncorrectDataException("Passwords don't match!");
        }
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.GET,
            produces = "application/json")
    @ApiOperation(value = "Login",
            tags = "get",
            response = ProviderDto.class)
    @ApiResponse(code = 200, message = "Success")
    public Boolean login(@RequestParam String email,
                         @RequestParam String password) {
        email = StringUtils.trimToNull(email);
        password = StringUtils.trimToNull(password);

        return accountOwnerService.login(email, password);
    }
}
