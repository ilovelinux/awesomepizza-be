package com.awesomepizza.be.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;


@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    PENDING(0),
    ACCEPTED(1),
    IN_PREPARATION(2),
    READY(3),
    IN_DELIVERY(4),
    NOT_DELIVERED(4),
    DELIVERED(100),
    CANCELLED(100),
    ;

    private int level;

    @Accessors(fluent = true) @Getter static final int BLOCK_ORDER_VALUE = 100;
}

