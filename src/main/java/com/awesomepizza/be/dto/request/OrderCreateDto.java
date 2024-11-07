package com.awesomepizza.be.dto.request;

import java.util.List;

import com.awesomepizza.be.dto.AbstractOrderDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderCreateDto extends AbstractOrderDto {
    @JsonProperty(value = "delivery_address_id")
    Long deliveryAddressId;

    @JsonProperty(value = "products")
    List<Long> products;
}
