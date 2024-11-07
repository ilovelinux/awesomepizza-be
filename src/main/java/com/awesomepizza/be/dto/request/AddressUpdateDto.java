package com.awesomepizza.be.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AddressUpdateDto extends AddressCreateDto {
    @JsonProperty(value="id")
    Long id;

    @JsonProperty(value="notes")
    String notes;
}