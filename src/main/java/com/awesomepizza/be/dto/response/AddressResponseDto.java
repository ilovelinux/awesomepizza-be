package com.awesomepizza.be.dto.response;

import com.awesomepizza.be.dto.AbstractAddressDto;
import com.awesomepizza.be.model.AddressModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AddressResponseDto extends AbstractAddressDto {
    public static AddressResponseDto of(AddressModel addressModel) {
        final AddressResponseDto addressResponse = new AddressResponseDto();

        addressResponse.importModel(addressModel);

        addressResponse.setId(addressModel.getId());
        addressResponse.setNotes(addressModel.getNotes());

        return addressResponse;
    }

    @JsonProperty(value="id")
    Long id;

    @JsonProperty(value="notes")
    String notes;
}