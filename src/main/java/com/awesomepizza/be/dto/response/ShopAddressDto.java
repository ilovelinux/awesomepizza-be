package com.awesomepizza.be.dto.response;

import com.awesomepizza.be.dto.AbstractAddressDto;
import com.awesomepizza.be.model.AbstractAddressModel;
import com.awesomepizza.be.model.ShopModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ShopAddressDto extends AbstractAddressDto {
    public static ShopAddressDto of(ShopModel addressModel) {
        final ShopAddressDto addressResponse = new ShopAddressDto();

        addressResponse.importModel((AbstractAddressModel) addressModel);

        return addressResponse;
    }
}
