package com.awesomepizza.be.dto.request;

import com.awesomepizza.be.dto.AbstractAddressDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressCreateDto extends AbstractAddressDto {
    @JsonProperty(value="notes")
    String notes;
}