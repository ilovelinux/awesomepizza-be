package com.awesomepizza.be.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.awesomepizza.be.dto.request.AddressCreateDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Table(name = "ADDRESSES")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class AddressModel extends AbstractAddressModel {
    public static AddressModel of(AddressCreateDto address, CustomerModel customer) {
        final AddressModel addressModel = new AddressModel();

        addressModel.setStreet(address.getStreet());
        addressModel.setStreetNo(address.getStreetNo());
        addressModel.setCity(address.getCity());
        addressModel.setZip_cap(address.getZip_cap());
        addressModel.setProv_st(address.getProv_st());
        addressModel.setState(address.getState());
        addressModel.setNotes(address.getNotes());

        addressModel.setCustomerId(customer.getId());
        addressModel.setCustomer(customer);

        return addressModel;
    }

    @Id
    Long id;

    @Column("customer")
    @NotNull
    Long customerId;

    @Transient
    CustomerModel customer;

    @Size(max = 30)
    String notes;
}
