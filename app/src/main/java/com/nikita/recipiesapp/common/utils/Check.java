package com.nikita.recipiesapp.common.utils;


public final class Check {
  public static void notNull(Object value) {
    notNull(value, value.toString() + " is null");
  }

  public static void notNull(Object value, String message) {
    if (value == null) throw new IllegalStateException(message);
  }
}
