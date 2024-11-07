package com.awesomepizza.be.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustomerUpdateDto {
    @JsonProperty(value = "name")
    String name;
}
