package com.tkft.sell.repository;

import com.tkft.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123458");
        productInfo.setCategoryType(53);
        productInfo.setProductDescription("好吃好吃");
        productInfo.setProductName("香蕉");
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("www.apple.com");
        productInfo.setProductPrice(new BigDecimal(3.5));
        productInfo.setProductStock(100);

        ProductInfo result = repository.save(productInfo);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByProductStatus() {
        List<ProductInfo> result = repository.findByProductStatus(0);
        Assert.assertNotEquals(0, result.size());
    }
}