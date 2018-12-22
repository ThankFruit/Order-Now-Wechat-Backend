package com.tkft.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Auther xinyuan
 * @Date 11/20/18 9:12 PM
 **/
@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openid必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;

}
