package com.criteo.demo.tracker.model;

import java.io.Serializable;

public class ViewProduct implements Serializable {

    private int userId;
    private int productId;
    private long timestamp;

    public ViewProduct(int userId, int productId, long timestamp) {
        this.userId = userId;
        this.productId = productId;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getProductId() {
        return productId;
    }

    public int getUserId() {
        return userId;
    }
}
