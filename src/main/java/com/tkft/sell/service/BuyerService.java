package com.tkft.sell.service;

import com.tkft.sell.dto.OrderDTO;

/**
 * @Auther xinyuan
 * @Date 11/21/18 3:14 PM
 **/
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
