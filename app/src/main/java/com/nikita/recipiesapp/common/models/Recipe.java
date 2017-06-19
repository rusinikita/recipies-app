package com.nikita.recipiesapp.common.models;


import java.util.List;

public final class Recipe {
  public final int id;
  public final String name;
  public final List<Ingredient> ingredients;
  public final List<Step> steps;
  public final int servings;
  public final String image;

  public Recipe(int id,
                String name,
                List<Ingredient> ingredients,
                List<Step> steps,
                int servings,
                String image) {
    this.id = id;
    this.name = name;
    this.ingredients = ingredients;
    this.steps = steps;
    this.servings = servings;
    this.image = image;
  }
}
