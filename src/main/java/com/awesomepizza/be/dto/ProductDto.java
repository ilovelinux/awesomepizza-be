package com.awesomepizza.be.dto;

import java.util.List;

import com.awesomepizza.be.model.ProductModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    public static ProductDto of(ProductModel productModel) {
        final Long id = productModel.getId();
        final String name = productModel.getName();
        final String description = productModel.getDescription();
        final List<String> ingredients = productModel.getIngredients();
        final Double price = productModel.getPrice();

        return new ProductDto(id, name, description, ingredients, price);
    }

    @JsonProperty(value="id")
    Long id;

    @JsonProperty(value="name")
    String name;

    @JsonProperty(value="description")
    String description;

    @JsonProperty(value="ingredients")
    List<String> ingredients;

    @JsonProperty(value="price")
    Double price;
}
