/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.googlecode.bootstrapx.model;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum OrderBy implements TEnum {
  DESC(0),
  ASC(1);

  private final int value;

  private OrderBy(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static OrderBy findByValue(int value) { 
    switch (value) {
      case 0:
        return DESC;
      case 1:
        return ASC;
      default:
        return null;
    }
  }
}
