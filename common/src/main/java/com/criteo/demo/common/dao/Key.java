package com.criteo.demo.common.dao;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@PrimaryKeyClass
public class Key implements Serializable {

  @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private Integer userId;

  @PrimaryKeyColumn(name = "product_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
  private Integer productId;

  @PrimaryKeyColumn(name = "time", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
  private Date time;

  public Key(Integer userId, Integer productId, Date time) {
    this.userId = userId;
    this.productId = productId;
    this.time = time;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Key that = (Key) o;
    return Objects.equals(userId, that.userId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(time, that.time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, productId, time);
  }

  public Integer getProductId() {
    return productId;
  }

  public void setProductId(Integer productId) {
    this.productId = productId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }
}