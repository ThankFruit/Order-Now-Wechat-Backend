package com.tkft.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    //    类目ID
    @Id//主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

//    类目名称
    private String categoryName;

//    类目编号
    private Integer categoryType;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
