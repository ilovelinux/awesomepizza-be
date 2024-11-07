package com.awesomepizza.be.dto.response;

import java.time.LocalDateTime;

import com.awesomepizza.be.model.CustomerModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CustomerResponseDto {
    public static CustomerResponseDto of(CustomerModel customerModel) {
        final CustomerResponseDto customerResponse = new CustomerResponseDto();

        customerResponse.setId(customerModel.getId());
        customerResponse.setName(customerModel.getName());
        customerResponse.setCreationDate(customerModel.getCreationDate());
        customerResponse.setLastModifiedDate(customerModel.getLastModifiedDate());

        return customerResponse;
    }

    @JsonProperty(value = "id")
    Long id;

    @JsonProperty(value = "name")
    String name;

    @JsonProperty(value = "creation_date")
    LocalDateTime creationDate;

    @JsonProperty(value = "last_modified_date")
    LocalDateTime lastModifiedDate;
}
