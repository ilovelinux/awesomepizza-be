package com.awesomepizza.be.dto;

import com.awesomepizza.be.model.AbstractAddressModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode
@ToString
abstract public class AbstractAddressDto{
    protected void importModel(AbstractAddressModel addressModel) {
        this.setStreet(addressModel.getStreet());
        this.setStreetNo(addressModel.getStreetNo());
        this.setCity(addressModel.getCity());
        this.setZip_cap(addressModel.getZip_cap());
        this.setProv_st(addressModel.getProv_st());
        this.setState(addressModel.getState());
    }

    @JsonProperty(value="street")
    String street;

    @JsonProperty(value="street_no")
    String streetNo;

    @JsonProperty(value="city")
    String city;

    @JsonProperty(value="zip_cap")
    String zip_cap;

    @JsonProperty(value="prov_st")
    String prov_st;

    @JsonProperty(value="state")
    String state; // TODO: This should have predefined values
}