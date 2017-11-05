package com.nikita.recipiesapp.common.models;


public final class Ingredient {
  public final int quantity;
  public final String measure;
  public final String ingredient;
  public final String description;

  public Ingredient(int quantity, String measure, String ingredient) {
    this.quantity = quantity;
    this.measure = measure;
    this.ingredient = ingredient;
    this.description = ingredient + ", " + quantity + " " + measure;
  }
}
