package com.tkft.sell.enums;

import lombok.Getter;

/**
 * @Auther xinyuan
 * @Date 11/18/18 3:48 PM
 **/
@Getter
public enum OrderStatusEnum {
    NEW(0, "new order"),
    FINISHED(1, "finished order"),
    CANCEL(2, "canceled order"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
