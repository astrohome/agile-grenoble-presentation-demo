package com.criteo.demo.engine.model;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;

@Table("product_view")
public class ProductView implements Serializable {
    @PrimaryKey("user_id")
    private int userId;
    @Column("product_id")
    private int productId;
    @Column
    private long time;

    public ProductView(int userId, int productId, long time) {
        this.userId = userId;
        this.productId = productId;
        this.time = time;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
