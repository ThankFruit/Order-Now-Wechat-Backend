package com.tkft.sell.enums;

import lombok.Getter;

/**
 * @Auther xinyuan
 * @Date 11/18/18 3:53 PM
 **/
@Getter
public enum PayStatusEnum {
    WAIT(0, "not paid"),
    PAID(1, "paid"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
