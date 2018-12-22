package com.tkft.sell.service.impl;

import com.tkft.sell.converter.OrderMaster2OrderDTO;
import com.tkft.sell.dataobject.OrderDetail;
import com.tkft.sell.dataobject.OrderMaster;
import com.tkft.sell.dataobject.ProductInfo;
import com.tkft.sell.dto.CartDTO;
import com.tkft.sell.dto.OrderDTO;
import com.tkft.sell.enums.OrderStatusEnum;
import com.tkft.sell.enums.PayStatusEnum;
import com.tkft.sell.enums.ResultEnum;
import com.tkft.sell.exception.SellException;
import com.tkft.sell.repository.OrderDetailRepository;
import com.tkft.sell.repository.OrderMasterRepository;
import com.tkft.sell.service.OrderService;
import com.tkft.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Auther xinyuan
 * @Date 11/18/18 7:15 PM
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    //创建订单
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.genUniqueKey();
        List<CartDTO> cartDTOList = new ArrayList<>();
        //1. 查询商品（价格 数量）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail
                            .getProductQuantity())).add(orderAmount);
            //3. 订单详情入库
            //订单详情所有属性中 前端只传过来productId和productQuantity两个属性
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        //4. 订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //5 扣库存
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    //通过订单id查询订单
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> orderMaster = orderMasterRepository.findById(orderId);

        if (orderMaster.isPresent()) {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
            if(orderDetailList.isEmpty()){
                throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
            }
            //拼装orderDTO
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster.get(), orderDTO);
            orderDTO.setOrderDetailList(orderDetailList);
            return orderDTO;
        } else {
            //如果订单不存在 抛出异常
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
    }

    @Override
    @Transactional
    //通过用户微信openid查询用户分页订单 注意这里结果中只需要订单主表 不需要详情
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        //Page类型结果 content属性是list类型 里面是这一页的内容； TotalElement是所有结果个数（不同页的一起算）
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态 只有新下单的订单可以取消(其他两个状态为完结和已取消)
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            //更新失败
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返还库存
        if(orderDTO.getOrderDetailList().isEmpty()){
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOS);

        //如果用户已支付 需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.PAID.getCode())) {
            //TODO

        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态 只有新下单的订单可以被完结
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            //更新失败
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态 只有新下单的订单可以被完结
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.PAID.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            //更新失败
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
