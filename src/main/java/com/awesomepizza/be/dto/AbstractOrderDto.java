package com.awesomepizza.be.dto;

import com.awesomepizza.be.enums.OrderTypeEnum;
import com.awesomepizza.be.model.OrderModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
abstract public class AbstractOrderDto {
    protected void importModel(OrderModel orderModel) {
        this.setType(orderModel.getType());
        this.setNotes(orderModel.getNotes());
    }

    @JsonProperty(value = "type")
    OrderTypeEnum type;

    @JsonProperty(value = "notes")
    String notes;
}
