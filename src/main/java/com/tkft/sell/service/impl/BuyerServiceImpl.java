package com.tkft.sell.service.impl;

import com.tkft.sell.dto.OrderDTO;
import com.tkft.sell.enums.ResultEnum;
import com.tkft.sell.exception.SellException;
import com.tkft.sell.service.BuyerService;
import com.tkft.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tkft.sell.enums.ResultEnum.ORDER_OWNER_ERROR;

/**
 * @Auther xinyuan
 * @Date 11/21/18 3:16 PM
 **/
@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }
    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
