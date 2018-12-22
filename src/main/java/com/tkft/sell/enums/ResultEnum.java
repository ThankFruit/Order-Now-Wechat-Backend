package com.tkft.sell.enums;

import lombok.Getter;

/**
 * @Auther xinyuan
 * @Date 11/18/18 7:26 PM
 **/

@Getter
public enum ResultEnum {

    PARAM_ERROR(1, "wrong parament"),
    PRODUCT_NOT_EXIST(10, "product not exist"),
    PRODUCT_STOCK_ERROR(11, "out of stock"),
    ORDER_NOT_EXIST(12, "order not exist"),
    ORDERDETAIL_NOT_EXIST(13, "order detail not exist"),
    ORDER_STATUS_ERROR(14, "order status error"),
    ORDER_UPDATE_FAIL(15, "order update fail"),
    ORDER_DETAIL_EMPTY(16, "no product in the order"),
    ORDER_PAY_STATUS_ERROR(17, "order pay status error"),
    CART_EMPTY(18, "cart is empty"),
    ORDER_OWNER_ERROR(19, "order owner dont match"),
    WECHAT_MP_ERROR(20, "wechat error")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
