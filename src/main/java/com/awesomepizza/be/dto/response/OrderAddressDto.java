package com.awesomepizza.be.dto.response;

import com.awesomepizza.be.dto.AbstractAddressDto;
import com.awesomepizza.be.model.AbstractAddressModel;
import com.awesomepizza.be.model.OrderAddressModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderAddressDto extends AbstractAddressDto {
    public static OrderAddressDto of(OrderAddressModel addressModel) {
        final OrderAddressDto addressResponse = new OrderAddressDto();

        addressResponse.importModel((AbstractAddressModel) addressModel);

        return addressResponse;
    }
}
