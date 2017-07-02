package com.nikita.recipiesapp.common.models;


import java.util.List;

public final class Recipe {
  public final int id;
  public final String name;
  public final List<Ingredient> ingredients;
  public final List<Step> steps;
  public final int servings;
  private final String image;

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

  public String getImageUri() {
    return (image != null && !image.isEmpty()) ? image : "https://i0.wp.com/bakingamoment.com/wp-content/uploads/2015/03/4255featured2.jpg?w=720";
  }

  @Override
  public String toString() {
    return "{" +
      "id:" + id +
      "name:" + name +
      "ingredients:" + ingredients +
      "steps:" + steps +
      "servings:" + servings +
      "image:" + image +
      "}";
  }
}
