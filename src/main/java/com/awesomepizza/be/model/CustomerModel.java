package com.awesomepizza.be.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Table(name="CUSTOMERS")
@Getter
@Setter
@Accessors(chain=true)
public class CustomerModel extends AbstractGenericModel {
    @Id
    @Min(1)
    @Max(1)
    Long id;

    @Size(min=2, max=30)
    @NotBlank
    String name;
}
