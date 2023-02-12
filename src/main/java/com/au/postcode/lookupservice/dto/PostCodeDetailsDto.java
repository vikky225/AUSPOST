package com.au.postcode.lookupservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostCodeDetailsDto {
    private Integer id;
    private Integer postcode;
    private String suburb;
}
