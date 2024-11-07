package com.awesomepizza.be.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// TODO: Fine tuning Size(max=...)
abstract public class AbstractAddressModel extends AbstractGenericModel {
    @Size(max = 30)
    @NotBlank
    String street;

    @Size(max = 10)
    @NotBlank
    String streetNo;

    @Size(max = 30)
    @NotBlank
    String city;

    @Size(max = 10)
    @NotBlank
    String zip_cap;

    @Size(max = 30)
    @NotBlank
    String prov_st;

    @Size(max = 30)
    @NotBlank
    String state; // TODO: This should have predefined values
}
