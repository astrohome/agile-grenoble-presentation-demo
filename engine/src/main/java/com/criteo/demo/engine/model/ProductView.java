package com.criteo.demo.engine.model;

import com.criteo.demo.engine.dao.Key;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;

@Table("product_view")
public class ProductView implements Serializable {
    @PrimaryKey
    private Key key;

    public ProductView(int userId, int productId, long time) {
        this.key = new Key(userId, productId, time);
    }

    public Key getKey() {
        return key;
    }
}
