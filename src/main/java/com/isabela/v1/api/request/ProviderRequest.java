package com.isabela.v1.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProviderRequest {

    private String name;

    private String fiscalCode;

    private String country;

    private String county;

    private String address;

    private String bankAccount;
}
