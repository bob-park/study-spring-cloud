package com.example.orderservice.commons.dto.common.kafka;

public class Payload {

  private final String order_id;
  private final String user_id;
  private final String product_id;
  private final int qty;
  private final int unit_price;
  private final int total_price;

  private Payload(Builder builder) {
    this(
        builder.order_id,
        builder.user_id,
        builder.product_id,
        builder.qty,
        builder.unit_price,
        builder.total_price);
  }

  private Payload(
      String order_id,
      String user_id,
      String product_id,
      int qty,
      int unit_price,
      int total_price) {
    this.order_id = order_id;
    this.user_id = user_id;
    this.product_id = product_id;
    this.qty = qty;
    this.unit_price = unit_price;
    this.total_price = total_price;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getOrder_id() {
    return order_id;
  }

  public String getUser_id() {
    return user_id;
  }

  public String getProduct_id() {
    return product_id;
  }

  public int getQty() {
    return qty;
  }

  public int getUnit_price() {
    return unit_price;
  }

  public int getTotal_price() {
    return total_price;
  }

  @Override
  public String toString() {
    return "Payload{" +
            "order_id='" + order_id + '\'' +
            ", user_id='" + user_id + '\'' +
            ", product_id='" + product_id + '\'' +
            ", qty=" + qty +
            ", unit_price=" + unit_price +
            ", total_price=" + total_price +
            '}';
  }

  public static class Builder {
    private String order_id;
    private String user_id;
    private String product_id;
    private int qty;
    private int unit_price;
    private int total_price;

    private Builder() {}

    public Builder setOrder_id(String order_id) {
      this.order_id = order_id;
      return this;
    }

    public Builder setUser_id(String user_id) {
      this.user_id = user_id;
      return this;
    }

    public Builder setProduct_id(String product_id) {
      this.product_id = product_id;
      return this;
    }

    public Builder setQty(int qty) {
      this.qty = qty;
      return this;
    }

    public Builder setUnit_price(int unit_price) {
      this.unit_price = unit_price;
      return this;
    }

    public Builder setTotal_price(int total_price) {
      this.total_price = total_price;
      return this;
    }

    public Payload build() {
      return new Payload(this);
    }
  }
}
