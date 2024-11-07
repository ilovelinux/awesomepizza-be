package com.awesomepizza.be.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.experimental.Accessors;

/*
 * The DB user should be allowed to run only UPDATE since records [0..6] will
 * be inserted at DB initialization.
 */
@Table(name="SHOP_OPENING_HOURS")
@Getter
@Accessors(chain=true)
public class ShopOpeningHoursModel extends AbstractGenericModel {
    /**
     * Implemented as 0 = Monday, 1 = Tuesday ... 6 = Sunday.
     */
    @Id
    @Min(0)
    @Max(6)
    Long dayOfTheWeek;

    /**
     * Opening hours is an array of opening (index: [0, 2, 4, ...])
     * and closing (index: [1, 3, 5, ...]) hours.
     *
     * First element will be the opening hour of the first slot,
     * second element will be the closing hour of the first slot.
     * 
     * Example: if the store opens at 12:00AM, closes at 3:00PM for lunch, then
     *          opens again at 7:00PM and closes at 11:00PM, openingHours would
     *          be [12:00AM, 3:00PM, 7:00PM, 11:00PM]
     *
     * Because of that, Array size will always be even.
     */
    Set<Integer> openingHours;
}
