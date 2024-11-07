package com.awesomepizza.be.dto.response;

import java.util.List;

import com.awesomepizza.be.dto.AbstractOrderDto;
import com.awesomepizza.be.dto.ProductDto;
import com.awesomepizza.be.enums.OrderStatusEnum;
import com.awesomepizza.be.model.OrderModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderResponseDto extends AbstractOrderDto {
    public static OrderResponseDto of(OrderModel orderModel) {
        final OrderResponseDto orderResponse = new OrderResponseDto();

        orderResponse.importModel(orderModel);
        orderResponse.setId(orderModel.getId());
        orderResponse.setProducts(orderModel.getProducts().stream().map(ProductDto::of).toList());
        final OrderAddressDto address = OrderAddressDto.of(orderModel.getAddress());
        orderResponse.setDeliveryAddress(address);
        orderResponse.setStatus(orderModel.getStatus());

        return orderResponse;
    }

    @JsonProperty(value = "id")
    Long id;

    @JsonProperty(value = "products")
    List<ProductDto> products;

    @JsonProperty(value = "delivery_address")
    OrderAddressDto deliveryAddress;

    @JsonProperty(value = "notes")
    String notes;

    @JsonProperty(value = "status")
    OrderStatusEnum status;
}
