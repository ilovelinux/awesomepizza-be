package com.awesomepizza.be.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Table(name = "PRODUCTS")
@Getter
@Setter
@Accessors(chain = true)
public class ProductModel extends AbstractGenericModel {
    @Id
    Long id;

    @Size(min=3, max=30)
    @NotBlank
    String name;

    @Size(max=50)
    String description;

    List<String> ingredients;

    @NotNull
    @DecimalMin("0.1")
    Double price;

    Boolean visible;

    Boolean orderable;
}
