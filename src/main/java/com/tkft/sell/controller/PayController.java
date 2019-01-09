package com.tkft.sell.controller;

import com.tkft.sell.dto.OrderDTO;
import com.tkft.sell.enums.ResultEnum;
import com.tkft.sell.exception.SellException;
import com.tkft.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther xinyuan
 * @Date 11/24/18 2:15 PM
 **/
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private PayService payService;

    @GetMapping("/create")
//    public ModelAndView create(@RequestParam("orderId") String orderId,
//                               @RequestParam("returnUrl") String returnUrl,
//                               Map<String, Object> map) {
//
        public void create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl){
        //1. 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //2. 发起支付
//        PayResponse payResponse = payService.create(orderDTO);
//
//        map.put("payResponse", payResponse);
//        map.put("returnUrl", returnUrl);
//
//        return new ModelAndView("pay/create", map);
    }

    /**
     * 微信异步通知
     * @param notifyData
     */
//    @PostMapping("/notify")
//    public ModelAndView notify(@RequestBody String notifyData) {
//        payService.notify(notifyData);
//
//        //返回给微信处理结果
//        return new ModelAndView("pay/success");
//    }
}
