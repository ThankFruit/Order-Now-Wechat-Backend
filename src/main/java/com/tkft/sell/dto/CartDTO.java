package com.tkft.sell.dto;

import lombok.Data;

/**
 * 购物车
 * @Auther xinyuan
 * @Date 11/18/18 7:55 PM
 **/
@Data
public class CartDTO {
    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
