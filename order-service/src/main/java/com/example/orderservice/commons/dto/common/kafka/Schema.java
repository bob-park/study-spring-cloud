package com.example.orderservice.commons.dto.common.kafka;

import java.util.List;

public class Schema {

  private final String type;
  private final List<Field> fields;
  private final boolean optional;
  private final String name;

  private Schema(Builder builder) {
    this(builder.type, builder.fields, builder.optional, builder.name);
  }

  private Schema(String type, List<Field> fields, boolean optional, String name) {
    this.type = type;
    this.fields = fields;
    this.optional = optional;
    this.name = name;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getType() {
    return type;
  }

  public List<Field> getFields() {
    return fields;
  }

  public boolean isOptional() {
    return optional;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Schema{" +
            "type='" + type + '\'' +
            ", fields=" + fields +
            ", optional=" + optional +
            ", name='" + name + '\'' +
            '}';
  }

  public static class Builder {

    private String type;
    private List<Field> fields;
    private boolean optional;
    private String name;

    private Builder() {}

    public Builder setType(String type) {
      this.type = type;
      return this;
    }

    public Builder setFields(List<Field> fields) {
      this.fields = fields;
      return this;
    }

    public Builder setOptional(boolean optional) {
      this.optional = optional;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Schema build() {
      return new Schema(this);
    }
  }
}
