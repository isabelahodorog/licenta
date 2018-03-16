package com.isabela.v1.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InputDto {

    private String type;

    private Long inputId;

    private Long docNo;

    private Long providerId;

    private String providerName;

    private Date releaseDate;

    private Date dueDate;

    private Double value;

    private Double tva;

    private Double total;

    private Double toPay;
}
