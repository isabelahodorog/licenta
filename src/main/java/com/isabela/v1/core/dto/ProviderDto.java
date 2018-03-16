package com.isabela.v1.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProviderDto {

    private Long id;

    private String name;

    private String fiscalCode; //or CNP

    private String country;

    private String county;

    private String address;

    private String bankAccount;
}
