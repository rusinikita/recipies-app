package com.nikita.recipiesapp.common.models;


import android.support.annotation.Nullable;

import java.util.List;

public final class Recipe {
  public final int id;
  public final String name;
  public final List<Ingredient> ingredients;
  public final List<Step> steps;
  private final int servings;
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
    this.image = getImageUri(id, image);
  }

  private static String getImageUri(int id, @Nullable String image) {
    if (image != null && !image.isEmpty()) return image;

    String url;
    switch (id) {
      case 1:
        url = "https://i0.wp.com/bakingamoment.com/wp-content/uploads/2015/03/4255featured2.jpg?w=720";
        break;
      case 2:
        url = "https://d2gk7xgygi98cy.cloudfront.net/4-3-large.jpg";
        break;
      case 3:
        url = "https://dessertswithbenefits.com/wp-content/uploads/2014/01/33.jpg";
        break;
      case 4:
        url = "http://img.taste.com.au/3kqM4rqu/w720-h480-cfill-q80/taste/2016/11/classic-baked-vanilla-cheesecake-53202-1.jpeg";
        break;
      default:
        url = "http://img.taste.com.au/BcemwIdD/taste/2016/11/chocolate-celebration-cake-85607-1.jpeg";
    }
    return url;
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
