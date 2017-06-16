package com.nikita.recipiesapp.common.models;


import java.util.List;

public final class Recipe {
  public final String id;
  public final String name;
  public final List<String> ingredient;
  public final List<Step> steps;
  public final int servings;
  public final String image;

  public Recipe(String id,
                String name,
                List<String> ingredient,
                List<Step> steps,
                int servings,
                String image) {
    this.id = id;
    this.name = name;
    this.ingredient = ingredient;
    this.steps = steps;
    this.servings = servings;
    this.image = image;
  }
}
