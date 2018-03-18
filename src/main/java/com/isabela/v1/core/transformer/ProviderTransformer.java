package com.isabela.v1.core.transformer;

import com.isabela.v1.core.dto.ProviderDto;
import com.isabela.v1.core.model.Provider;
import org.apache.commons.collections4.Transformer;

public class ProviderTransformer implements Transformer<Provider, ProviderDto> {

    public ProviderDto transform(Provider in) {
        ProviderDto out = new ProviderDto();

        out.setId(in.getId());
        out.setFiscalCode(in.getFiscalCode());
        out.setName(in.getName());
        out.setBankAccount(in.getBankAccount());
        out.setAddress(in.getAddress().getAddress());
        out.setCountry(in.getAddress().getCountry());
        out.setCounty(in.getAddress().getCounty());

        return out;
    }
}
