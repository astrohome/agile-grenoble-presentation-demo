package com.criteo.demo.common.model;

import com.criteo.demo.common.dao.Key;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Table("product_view")
public class ProductView implements Serializable {
    @PrimaryKey
    private Key key;

    public ProductView() {}

    public ProductView(int userId, int productId, Date time) {
        this.key = new Key(userId, productId, time);
    }

    public Key getKey() {
        return key;
    }
}
