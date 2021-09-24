package com.example.orderservice.commons.dto.common.kafka;

public class Field {

  private final String type;
  private final boolean optional;
  private final String field;

  public Field(String type, boolean optional, String field) {
    this.type = type;
    this.optional = optional;
    this.field = field;
  }

  public String getType() {
    return type;
  }

  public boolean isOptional() {
    return optional;
  }

  public String getField() {
    return field;
  }

  @Override
  public String toString() {
    return "Field{"
        + "type='"
        + type
        + '\''
        + ", optional="
        + optional
        + ", field='"
        + field
        + '\''
        + '}';
  }
}
