package com.awesomepizza.be.dto.response;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import com.awesomepizza.be.model.ShopModel;
import com.awesomepizza.be.model.ShopOpeningHoursModel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class ShopInfoResponseDto {
    public static ShopInfoResponseDto of(ShopModel shopModel, List<ShopOpeningHoursModel> shopOpeningHours) {
        final String name = shopModel.getName();
        final ShopAddressDto address = ShopAddressDto.of(shopModel);
        final Map<Long, Set<Integer>> openingHours = shopOpeningHours
            .stream()
            .collect(Collectors.toMap(ShopOpeningHoursModel::getDayOfTheWeek, ShopOpeningHoursModel::getOpeningHours));

        return new ShopInfoResponseDto(name, address, openingHours);
    }

    @JsonProperty(value = "name")
    String name;

    @JsonProperty(value = "address")
    ShopAddressDto address;

    // TODO: Return list of intervals
    @JsonProperty(value = "opening_hours")
    Map<Long, Set<Integer>> openingHours;
}
