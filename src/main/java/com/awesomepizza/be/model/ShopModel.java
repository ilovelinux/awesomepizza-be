package com.awesomepizza.be.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.experimental.Accessors;

@Table(name="SHOPS")
@Getter
@Accessors(chain=true)
public class ShopModel extends AbstractAddressModel {
    /**
     * In this table there will be only one record since there's only one store
     * so id must be always one (1). This is guaranteed using @Min, @Max, and
     * and @Value together.
     */
    @Id
    @Min(1)
    @Max(1)
    @Value("1")
    Long id;

    /**
     * This name will be shown in the UI.
     */
    @Size(max=30)
    @NotBlank
    String name;
}
