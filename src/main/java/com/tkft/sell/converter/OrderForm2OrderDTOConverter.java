package com.tkft.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tkft.sell.dataobject.OrderDetail;
import com.tkft.sell.dto.OrderDTO;
import com.tkft.sell.enums.ResultEnum;
import com.tkft.sell.exception.SellException;
import com.tkft.sell.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther xinyuan
 * @Date 11/20/18 9:23 PM
 **/
public class OrderForm2OrderDTOConverter {
    public static OrderDTO converter(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerName(orderForm.getName());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        //使用gson把String Json转化成List<OrderDetail>
        try {
           orderDetailList =  gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
