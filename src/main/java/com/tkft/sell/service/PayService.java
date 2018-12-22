package com.tkft.sell.service;

import com.tkft.sell.dto.OrderDTO;

/**
 * @Auther xinyuan
 * @Date 11/24/18 2:20 PM
 **/
public interface PayService  {
    void create(OrderDTO orderDTO);
}
