package com.criteo.demo.common.model;

public class KafkaProductViewMessage {
    private int userId;
    private int productId;
    private long timestamp;

    public KafkaProductViewMessage() {}

    public KafkaProductViewMessage(int userId, int productId, long timestamp) {
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
