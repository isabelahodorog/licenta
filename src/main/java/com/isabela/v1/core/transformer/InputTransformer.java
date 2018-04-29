package com.isabela.v1.core.transformer;

import com.isabela.v1.core.dto.InputDto;
import com.isabela.v1.core.model.Input;
import org.apache.commons.collections4.Transformer;

public class InputTransformer implements Transformer<Input, InputDto> {
    /**
     * transforms class Input into dto of type InputDto
     *
     * @param in
     * @return
     */
    public InputDto transform(Input in) {
        InputDto out = new InputDto();

        out.setType(in.getType());
        out.setInputId(in.getId());
        out.setDocNo(in.getDocNo());
        out.setProviderId(in.getProviderId());
        out.setProviderName(in.getProvider().getName());
        out.setReleaseDate(in.getReleaseDate());
        out.setDueDate(in.getDueDate());
        out.setValue(in.getValue());
        out.setTva(in.getTva());
        out.setTotal(in.getTotal());
        out.setToPay(in.getToPay());

        return out;
    }
}
