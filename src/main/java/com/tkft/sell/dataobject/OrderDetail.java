package com.tkft.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther xinyuan
 * @Date 11/18/18 4:03 PM
 **/
@Entity
@Data
@DynamicUpdate
public class OrderDetail {
    @Id
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private String productIcon;

//    private Date createTime;
//
//    private Date updateTime;
}
