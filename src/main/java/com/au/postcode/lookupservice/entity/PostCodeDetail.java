package com.au.postcode.lookupservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("postcodedetail")
public class PostCodeDetail {
    @Id
    private Integer id;
    private Integer postcode;
    private String suburb;



}
