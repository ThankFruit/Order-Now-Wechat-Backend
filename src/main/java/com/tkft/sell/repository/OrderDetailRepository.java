package com.tkft.sell.repository;

import com.tkft.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * @Auther xinyuan
 * @Date 11/18/18 4:12 PM
 **/
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrderId(String orderId);
}
