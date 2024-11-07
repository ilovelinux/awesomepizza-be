package com.awesomepizza.be.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/*
 * This table should NOT allow UPDATE. Please restrict permissions of the DB
 * user to be sure that this won't happen.
 */
@Table(name = "ORDER_ADDRESSES")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class OrderAddressModel extends AbstractAddressModel {
    public static OrderAddressModel of(AddressModel address) {
        final OrderAddressModel orderAddressModel = new OrderAddressModel();

        orderAddressModel.setStreet(address.getStreet());
        orderAddressModel.setStreetNo(address.getStreetNo());
        orderAddressModel.setCity(address.getCity());
        orderAddressModel.setZip_cap(address.getZip_cap());
        orderAddressModel.setProv_st(address.getProv_st());
        orderAddressModel.setState(address.getState());
        orderAddressModel.setNotes(address.getNotes());

        return orderAddressModel;
    }

    @Id
    Long id;

    @Size(max = 30)
    String notes;
}
