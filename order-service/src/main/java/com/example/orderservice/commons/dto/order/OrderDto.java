package com.example.orderservice.commons.dto.order;

import java.io.Serializable;

public class OrderDto implements Serializable {

  private String productId;
  private Integer qty;
  private Integer unitPrice;
  private Integer totalPrice;

  private String orderId;
  private String userId;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  public Integer getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Integer unitPrice) {
    this.unitPrice = unitPrice;
  }

  public Integer getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Integer totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "OrderDto{"
        + "productId='"
        + productId
        + '\''
        + ", qty="
        + qty
        + ", unitPrice="
        + unitPrice
        + ", totalPrice="
        + totalPrice
        + ", orderId='"
        + orderId
        + '\''
        + ", userId='"
        + userId
        + '\''
        + '}';
  }
}
